package com.finreach.paymentservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom @{@link PaymentServiceException} for Invalid Amount
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidAmountException extends PaymentServiceException {

    public InvalidAmountException(String message) {
        super(message);
    }
}
