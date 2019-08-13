package com.finreach.paymentservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class TransactionResponse {

    private String id;
    private String account;
    private Double amount;
    private Date created;
}
