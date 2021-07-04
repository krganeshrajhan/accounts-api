package com.anz.accounts.service;

import com.anz.accounts.exception.AccountsException;
import com.anz.accounts.model.Account;
import com.anz.accounts.model.Transaction;
import com.anz.accounts.model.User;
import com.anz.accounts.repository.AccountRepository;
import com.anz.accounts.repository.TransactionRepository;
import com.anz.accounts.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(AccountService.class)
public class AccountServiceTest {

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Test
    public void givenInvalidUserIdRetrieveAccountsThrowException() {
        when(accountRepository.findAllByUser(User.builder().userId("1").build())).thenReturn(new ArrayList<>());
        when(userRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(AccountsException.class, () -> {
            accountService.findAccountsByUserId("1");
        });
    }

    @Test
    public void givenValidUserIdRetrieveAccounts() throws AccountsException {
        User user = User.builder().userId("1").userName("user").build();
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(Account.builder().accountNumber("123456").accountName("SGDEBITAC").accountType("Savings")
                .user(user).build());
        accounts.add(Account.builder().accountNumber("123523").accountName("SGCREDITAC").accountType("Current")
                .user(user).build());
        when(accountRepository.findAllByUser(user)).thenReturn(accounts);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        List<Account> accountList = accountService.findAccountsByUserId("1");
        assertEquals(2, accountList.size());
        assertEquals("123456", accountList.get(0).getAccountNumber());
        assertEquals("SGDEBITAC", accountList.get(0).getAccountName());
        assertEquals("Savings", accountList.get(0).getAccountType());
    }

    @Test
    public void givenInvalidAccountIdRetrieveTransactionsThrowsException() {
        assertThrows(AccountsException.class, () -> {
           accountService.findTransactionsByAccountId("123456");
        });
    }

    @Test
    public void givenValidAcounIdRetrieveTransactions() throws AccountsException {
        User user = User.builder().userId("1").userName("user").build();
        ArrayList<Account> accounts = new ArrayList<>();
        Account account1 = Account.builder().accountNumber("123456").accountName("SGDEBITAC").accountType("Savings")
                .user(user).build();
        accounts.add(account1);
        Account account2 = Account.builder().accountNumber("123523").accountName("SGCREDITAC").accountType("Current")
                .user(user).build();
        accounts.add(account2);
        Account account3 = Account.builder().accountNumber("123412").accountName("SGCREDITAC").accountType("Savings")
                .user(user).build();
        accounts.add(account3);
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(Transaction.builder().id("1").debitAccount(account1).creditAccount(account2)
                .transactionType("Debit").valueDate(LocalDate.now()).amount(20.56).build());
        transactions.add(Transaction.builder().id("2").debitAccount(account2).creditAccount(account3)
                .transactionType("Debit").valueDate(LocalDate.now()).amount(20.56).build());
        when(accountRepository.findById("123456")).thenReturn(Optional.of(account2));
        when(transactionRepository.findAllByCreditAccountOrDebitAccount(account2, account2)).thenReturn(transactions);
        List<Transaction> transactionList = accountService.findTransactionsByAccountId("123456");
        assertEquals(2, transactionList.size());
    }

}
