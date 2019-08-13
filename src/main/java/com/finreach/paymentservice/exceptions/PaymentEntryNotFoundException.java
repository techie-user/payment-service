package com.finreach.paymentservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PaymentEntryNotFoundException extends PaymentServiceException {

    public PaymentEntryNotFoundException(String message) {
        super(message);
    }
}
