package com.finreach.paymentservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom @{@link PaymentServiceException} for Payment Record Not Found
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidPaymentStateTransitionException extends PaymentServiceException {

    public InvalidPaymentStateTransitionException(String message) {
        super(message);
    }

}
