package com.anz.accounts.repository;

import com.anz.accounts.model.Account;
import com.anz.accounts.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Page<Transaction> findAllByCreditAccountOrDebitAccount(Account creditAccount, Account debitAccount, Pageable pageable);
}
