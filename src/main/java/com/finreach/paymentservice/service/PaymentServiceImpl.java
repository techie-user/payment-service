package com.finreach.paymentservice.service;

import com.finreach.paymentservice.domain.Account;
import com.finreach.paymentservice.domain.Payment;
import com.finreach.paymentservice.domainvalue.PaymentStatus;
import com.finreach.paymentservice.exceptions.*;
import com.finreach.paymentservice.store.PaymentStore;
import com.finreach.paymentservice.validator.PaymentStateValidator;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentStore paymentStore;

    private final AccountService accountService;

    public PaymentServiceImpl(PaymentStore paymentStore, AccountService accountService) {
        this.paymentStore = paymentStore;
        this.accountService = accountService;
    }

    @Override
    public Payment getPaymentDetails(String id) throws PaymentEntryNotFoundException {
        return paymentStore.findById(id).orElseThrow(() -> new PaymentEntryNotFoundException("Payment Entry not found for id :" + id));
    }

    /**
     * Fetch all @{@link Payment}
     */
    @Override
    public List<Payment> getAllPayments() {
        return paymentStore.findAll();
    }

    /**
     * Creates a new {@link Payment}
     *
     * @param request, the {@link }
     * @throws AccountNotFoundException if the account is not found.
     */
    @Override
    public Payment createPayment(Payment request) throws AccountNotFoundException, InvalidAmountException {

        if (null != accountService.getAccountDetails(request.getSourceAccountId()) && null != accountService.getAccountDetails(request.getDestinationAccountId())) {
            if (request.getDestinationAccountId().equalsIgnoreCase(request.getSourceAccountId())) {
                throw new InvalidAccountException("Destination Account Id & Source Account Id cannot be same");
            }
            if (request.getAmount() > 0) {
                final String id = String.valueOf(System.nanoTime());
                Payment payment = new Payment.PaymentDTOBuilder(id).withAmount(request.getAmount()).withDestinationAccountId(request.getDestinationAccountId()).withSourceAccountId(request.getSourceAccountId()).withState(PaymentStatus.CREATED.getStatus()).withCreatedDt(new Date()).build();
                paymentStore.save(payment);
                return payment;
            } else {
                throw new InvalidAmountException("Please provide a non-negative value for amount instead of the given value " + request.getAmount());
            }
        } else {
            throw new AccountNotFoundException("Account Not Found for id :" + request.getSourceAccountId());
        }
    }

    /**
     * Searches for {@link Payment} by payment Id and changes the status to Executed.
     *
     * @param id, the id of the {@link Payment}
     * @throws AccountNotFoundException      if the account entry is not found.
     * @throws PaymentEntryNotFoundException if payment entry is not found.
     */
    @Override
    public Payment executePayment(String id) throws PaymentEntryNotFoundException, AccountNotFoundException {
        final Optional<Payment> paymentEntry = paymentStore.findById(id);
        if (!paymentEntry.isPresent()) {
            throw new PaymentEntryNotFoundException("Payment Entry not found for id :" + id);
        }
        Payment payment = paymentEntry.get();
        if (!PaymentStateValidator.isPaymentAlreadyExecuted().apply(payment) && !PaymentStateValidator.isPaymentAlreadyCancelled().apply(payment)) {
            Account sourceAccount = accountService.getAccountDetails(payment.getSourceAccountId());
            if (null != sourceAccount) {
                if (null != sourceAccount.getBalance() && sourceAccount.getBalance() >= payment.getAmount()) {
                    Account destinationAccount = accountService.getAccountDetails(payment.getDestinationAccountId());
                    if (null != destinationAccount) {
                        //Debit from source account
                        accountService.addTransaction(payment.getSourceAccountId(), payment.getAmount() * -1);
                        //Credit to destination account
                        accountService.addTransaction(payment.getDestinationAccountId(), payment.getAmount());
                        payment.setState(PaymentStatus.EXECUTED.getStatus());
                        payment.setUpdatedDate(new Date());
                        paymentStore.update(payment);
                        return payment;
                    } else {
                        throw new AccountNotFoundException("Account Not Found for id :" + payment.getDestinationAccountId());
                    }
                } else {
                    //Insufficient Source Account balance
                    payment.setState(PaymentStatus.REJECTED.getStatus());
                    payment.setUpdatedDate(new Date());
                    paymentStore.update(payment);
                    return payment;
                }
            } else {
                throw new AccountNotFoundException("Account entry not found for id :" + payment.getSourceAccountId());
            }
        } else {
            throw new InvalidPaymentStateTransitionException("Invalid Payment State Transition from status:" + payment.getState() + " for payment id : " + payment.getId());
        }
    }

    /**
     * Searches for {@link Payment} by payment Id and changes the status to Cancelled.
     *
     * @param id, the id of the {@link Payment}
     * @throws PaymentEntryNotFoundException          if payment record is not found.
     * @throws InvalidPaymentStateTransitionException if payment state is not valid.
     */
    @Override
    public Payment cancelPayment(String id) throws PaymentEntryNotFoundException, InvalidPaymentStateTransitionException {
        final Optional<Payment> paymentOpt = paymentStore.findById(id);
        if (!paymentOpt.isPresent()) {
            throw new PaymentEntryNotFoundException("Payment Not Found for id :" + id);
        }

        Payment payment = paymentOpt.get();
        if (!PaymentStateValidator.isPaymentAlreadyExecuted().apply(payment) && !PaymentStateValidator.isPaymentAlreadyCancelled().apply(payment) && !PaymentStateValidator.isPaymentAlreadyRejected().apply(payment)) {
            payment.setState(PaymentStatus.CANCELLED.getStatus());
            payment.setUpdatedDate(new Date());
            paymentStore.update(payment);
            return payment;
        } else {
            throw new InvalidPaymentStateTransitionException("Invalid Payment State Transition from status:" + payment.getState() + " for payment id : " + payment.getId());
        }

    }

}
