package com.finreach.paymentservice.service;

import com.finreach.paymentservice.domain.Transaction;
import com.finreach.paymentservice.store.TransactionStore;
import com.finreach.paymentservice.util.MathUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionStore transactionStore;

    public TransactionServiceImpl(TransactionStore transactionStore) {
        this.transactionStore = transactionStore;
    }

    /**
     * Creates a new {@link Transaction}
     *
     * @param account, the account ID
     * @param amount,  the amount
     */
    @Override
    public Transaction create(String account, Double amount) {
        final Date created = Date.from(Instant.now());
        final Transaction transaction = new Transaction(MathUtils.getCurrentTimeInNanoSeconds(), account, amount, created);
        transactionStore.save(transaction);
        return transaction;
    }

    @Override
    public Map<String, List<Transaction>> search(Integer second) {
        return transactionStore.search(second);
    }

}