package com.finreach.paymentservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private String id;
    private Double balance;
    private List<TransactionResponse> transactions;
}
