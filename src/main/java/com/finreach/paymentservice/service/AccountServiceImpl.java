package com.finreach.paymentservice.service;

import com.finreach.paymentservice.domain.Account;
import com.finreach.paymentservice.domain.Transaction;
import com.finreach.paymentservice.exceptions.AccountNotFoundException;
import com.finreach.paymentservice.store.AccountStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountStore accountStore;

    private final TransactionService transactionService;

    public AccountServiceImpl(AccountStore accountStore, TransactionService transactionService) {
        this.accountStore = accountStore;
        this.transactionService = transactionService;
    }

    /**
     * @param account Creates a new {@link Account}
     * @return created account instance
     */
    @Override
    public Account addAccountsEntry(Account account) {
        return accountStore.save(account);
    }

    /**
     * Searches {@link Account} by account Id.
     *
     * @param id, the id of the {@link Account}
     */
    @Override
    public Account getAccountDetails(String id) throws AccountNotFoundException {
        final Optional<Account> accountOpt = accountStore.findById(id);
        if (!accountOpt.isPresent()) {
            throw new AccountNotFoundException("Account entry not found for id :" + id);
        }
        return accountOpt.get();
    }

    /**
     * Fetch all @{@link Account}
     */
    @Override
    public List<Account> getAllAccounts() {
        return accountStore.findAll();
    }

    /**
     * Creates a new {@link Transaction} for an @{@link Account}
     *
     * @param id,     the id of the {@link Account}
     * @param amount, the amount to be credited.
     */
    @Override
    public void addTransaction(String id, Double amount) throws AccountNotFoundException {
        final Optional<Account> accountOpt = accountStore.findById(id);
        if (!accountOpt.isPresent()) {
            throw new AccountNotFoundException("Account entry not found for id :" + id);
        }

        Account account = accountOpt.get();
        account.addTransaction(transactionService.create(id, amount));
        account.updateBalance(amount);
        accountStore.save(account);
    }
}
