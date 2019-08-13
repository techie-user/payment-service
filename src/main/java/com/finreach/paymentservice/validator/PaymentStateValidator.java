package com.finreach.paymentservice.validator;

import com.finreach.paymentservice.domain.Payment;
import com.finreach.paymentservice.domainvalue.PaymentStatus;

import java.util.function.Function;

/**
 * Functional Interface to validate the Payment state(s)
 */
public interface PaymentStateValidator extends Function<Payment, Boolean> {

    static PaymentStateValidator isPaymentAlreadyExecuted() {
        return payment -> payment.getState().equalsIgnoreCase(PaymentStatus.EXECUTED.getStatus());
    }

    static PaymentStateValidator isPaymentAlreadyCancelled() {
        return payment -> payment.getState().equalsIgnoreCase(PaymentStatus.CANCELLED.getStatus());
    }

    static PaymentStateValidator isPaymentAlreadyRejected() {
        return payment -> payment.getState().equalsIgnoreCase(PaymentStatus.REJECTED.getStatus());
    }
}