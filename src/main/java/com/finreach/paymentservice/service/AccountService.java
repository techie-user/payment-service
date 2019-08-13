package com.finreach.paymentservice.service;

import com.finreach.paymentservice.domain.Account;
import com.finreach.paymentservice.exceptions.AccountNotFoundException;

import javax.validation.Valid;
import java.util.List;

public interface AccountService {

    Account addAccountsEntry(@Valid Account request);

    Account getAccountDetails(String id) throws AccountNotFoundException;

    List<Account> getAllAccounts();

    void addTransaction(String id, Double amount) throws AccountNotFoundException;
}
