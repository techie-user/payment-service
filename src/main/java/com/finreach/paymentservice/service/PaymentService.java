package com.finreach.paymentservice.service;

import com.finreach.paymentservice.domain.Payment;
import com.finreach.paymentservice.exceptions.AccountNotFoundException;
import com.finreach.paymentservice.exceptions.InvalidPaymentStateTransitionException;
import com.finreach.paymentservice.exceptions.PaymentEntryNotFoundException;

import java.util.List;

public interface PaymentService {

    Payment getPaymentDetails(String id);

    List<Payment> getAllPayments();

    Payment createPayment(Payment payment);

    Payment executePayment(String id) throws PaymentEntryNotFoundException, AccountNotFoundException;

    Payment cancelPayment(String id) throws PaymentEntryNotFoundException, InvalidPaymentStateTransitionException;
}
