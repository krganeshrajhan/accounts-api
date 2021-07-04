package com.anz.accounts.repository;

import com.anz.accounts.model.Account;
import com.anz.accounts.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByCreditAccountOrDebitAccount(Account creditAccount, Account debitAccount);
}
