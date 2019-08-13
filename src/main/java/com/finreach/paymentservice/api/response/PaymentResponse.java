package com.finreach.paymentservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private String id;
    private Double amount;
    private String sourceAccountId;
    private String destinationAccountId;
    private Date createdDate;
    private Date updatedDate;
    private String state;
}
