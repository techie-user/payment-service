package com.finreach.paymentservice.store;

import com.finreach.paymentservice.domain.Transaction;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransactionStore {

    private static final Map<String, Transaction> TRANSACTIONS = new ConcurrentHashMap<>();

    public void save(Transaction transaction) {
        TRANSACTIONS.put(transaction.getId(), transaction);
    }

    public Map<String, List<Transaction>> search(Integer second) {
        Date now = new Date();
        if (!TRANSACTIONS.isEmpty()) {
            Map<String, List<Transaction>> map = new HashMap<>();
            for (Transaction transaction : TRANSACTIONS.values()) {
                if (transaction.getAmount() > 0 && (now.getTime() - transaction.getCreated().getTime() < (second * 1000))) {
                    map.computeIfAbsent(transaction.getAccount(), k -> new ArrayList<>()).add(transaction);
                }
            }
            return map;
        }
        return null;
    }

}
