package com.anz.accounts.service;

import com.anz.accounts.exception.AccountsException;
import com.anz.accounts.model.Account;
import com.anz.accounts.model.Transaction;
import com.anz.accounts.model.User;
import com.anz.accounts.repository.AccountRepository;
import com.anz.accounts.repository.TransactionRepository;
import com.anz.accounts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Account> findAccountsByUserId(String userId) throws AccountsException {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            throw new AccountsException("Invalid User");
        }

        return accountRepository.findAllByUser(user.get());
    }


    public List<Transaction> findTransactionsByAccountId(String transactionId) throws AccountsException {
        Optional<Account> account = accountRepository.findById(transactionId);
        if(!account.isPresent()) {
            throw new AccountsException("Invalid Account");
        }
        return transactionRepository.findAllByCreditAccountOrDebitAccount(account.get(), account.get());
    }
}
