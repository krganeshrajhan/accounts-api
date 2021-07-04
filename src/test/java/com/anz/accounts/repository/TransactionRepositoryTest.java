package com.anz.accounts.repository;

import com.anz.accounts.model.Account;
import com.anz.accounts.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    @Sql("/create_transactions.sql")
    public void givenAccountNumberReturnTransactionList() {
        User user = User.builder().userId("1").build();
        Account account1 = Account.builder().accountNumber("123456").user(user).build();
        Account account2 = Account.builder().accountNumber("134567").user(user).build();
        Account account3 = Account.builder().accountNumber("123534").user(user).build();
        assertEquals(1, transactionRepository.findAllByCreditAccountOrDebitAccount(account1, account1, PageRequest.of(0, 2)).getContent().size());
        assertEquals(1, transactionRepository.findAllByCreditAccountOrDebitAccount(account2, account2, PageRequest.of(0, 1)).getContent().size());
        assertEquals(1, transactionRepository.findAllByCreditAccountOrDebitAccount(account2, account2, PageRequest.of(1, 1)).getContent().size());
        assertEquals(1, transactionRepository.findAllByCreditAccountOrDebitAccount(account3, account3, PageRequest.of(0, 2)).getContent().size());
    }

}
