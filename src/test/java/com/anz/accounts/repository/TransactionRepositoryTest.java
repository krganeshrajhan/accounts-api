package com.anz.accounts.repository;

import com.anz.accounts.model.Account;
import com.anz.accounts.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    @Sql("/create_transactions.sql")
    public void givenAccountNumberReturnTransactionList() {
        User user = new User("1", null, null);
        Account account1 = new Account("123456","", "", user);
        Account account2 = new Account("134567","", "", user);
        Account account3 = new Account("123534","", "", user);
        assertEquals(1, transactionRepository.findAllByCreditAccountOrDebitAccount(account1, account1).size());
        assertEquals(2, transactionRepository.findAllByCreditAccountOrDebitAccount(account2, account2).size());
        assertEquals(1, transactionRepository.findAllByCreditAccountOrDebitAccount(account3, account3).size());
    }

}
