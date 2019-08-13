package com.finreach.paymentservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom @{@link PaymentServiceException} when Destination_Account == Source_Account
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidAccountException extends PaymentServiceException {

    public InvalidAccountException(String message) {
        super(message);
    }

}
