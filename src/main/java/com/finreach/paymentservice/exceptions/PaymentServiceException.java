package com.finreach.paymentservice.exceptions;

public class PaymentServiceException extends RuntimeException {

    PaymentServiceException(String message) {
        super(message);
    }
}
