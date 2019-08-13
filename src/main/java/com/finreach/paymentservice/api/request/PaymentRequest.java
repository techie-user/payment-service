package com.finreach.paymentservice.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @NotNull(message = "Amount is mandatory.")
    private Double amount;

    @NotNull(message = "Source ID is required.")
    private String sourceAccountId;

    @NotNull(message = "Destination ID is required.")
    private String destinationAccountId;
}
