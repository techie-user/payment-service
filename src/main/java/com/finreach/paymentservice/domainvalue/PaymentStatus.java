package com.finreach.paymentservice.domainvalue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    CREATED(0, "CREATED"), EXECUTED(1, "EXECUTED"), REJECTED(2, "REJECTED"), CANCELLED(3, "CANCELED");

    private int id;
    private String status;
}
