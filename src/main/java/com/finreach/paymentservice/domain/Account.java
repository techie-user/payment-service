package com.finreach.paymentservice.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private String id;
    private Double balance = 0d;
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String id) {
        this.id = id;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void updateBalance(Double amount) {
        balance = Double.sum(balance, amount);
    }

    @Override
    public String toString() {
        return id;
    }
}
