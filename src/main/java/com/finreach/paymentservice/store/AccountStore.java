package com.finreach.paymentservice.store;

import com.finreach.paymentservice.domain.Account;
import com.finreach.paymentservice.util.MathUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccountStore {

    private static final Map<String, Account> ACCOUNTS = new ConcurrentHashMap<>();

    public Account save(Account account) {
        String id = MathUtils.getCurrentTimeInNanoSeconds();
        account.setId(id);
        ACCOUNTS.put(id, account);
        return ACCOUNTS.get(id);
    }

    public Optional<Account> findById(String id) {
        return Optional.ofNullable(ACCOUNTS.get(id));
    }

    public List<Account> findAll() {
        return new ArrayList<>(ACCOUNTS.values());
    }

}
