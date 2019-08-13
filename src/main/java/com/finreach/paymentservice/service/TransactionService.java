package com.finreach.paymentservice.service;

import com.finreach.paymentservice.domain.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {

    /**
     * Creates a new {@link Transaction}
     *
     * @param account, the account ID
     * @param amount,  the amount
     */
    Transaction create(String account, Double amount);

    Map<String, List<Transaction>> search(Integer second);
}
