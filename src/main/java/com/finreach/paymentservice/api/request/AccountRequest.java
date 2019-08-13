package com.finreach.paymentservice.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AccountRequest {

    @PositiveOrZero(message = "Account Balance cannot be negative.")
    @NotNull(message = "Account Balance is required")
    private Double balance;
}
