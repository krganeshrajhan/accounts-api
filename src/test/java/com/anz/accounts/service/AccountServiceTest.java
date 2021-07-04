package com.anz.accounts.service;

import com.anz.accounts.dto.AccountDto;
import com.anz.accounts.dto.ApiResponseDto;
import com.anz.accounts.dto.TransactionDto;
import com.anz.accounts.exception.AccountsException;
import com.anz.accounts.mapper.AccountsMapper;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @MockBean
    private AccountsMapper mapper;

    @Autowired
    private AccountService accountService;

    @Test
    public void givenInvalidUserIdRetrieveAccountsThrowException() {
        Page page = new PageImpl<List<Account>>(new ArrayList<>());
        when(accountRepository.findAllByUser(User.builder().userId("1").build(), PageRequest.of(0, 2)))
                .thenReturn(page);
        when(userRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(AccountsException.class, () -> {
            accountService.findAccountsByUserId("1",0, 2);
        });
    }

    @Test
    public void givenValidUserIdRetrieveAccounts() throws AccountsException {
        User user = User.builder().userId("1").userName("user").build();
        ArrayList<Account> accounts = new ArrayList<>();
        Account account1 = Account.builder().accountNumber("123456").accountName("SGDEBITAC").accountType("Savings")
                .balanceDate(LocalDate.of(2021,07,04)).currency("SGD").availableBalance(345.6).user(user).build();
        accounts.add(account1);
        accounts.add(Account.builder().accountNumber("123523").accountName("SGCREDITAC").accountType("Current")
                .user(user).build());
        when(accountRepository.findAllByUser(user, PageRequest.of(0, 2)))
                .thenReturn(new PageImpl(accounts));
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        List<AccountDto> accountDtos = new ArrayList<>();
        accountDtos.add(AccountDto.builder().accountNumber("123456").accountName("SGDEBITAC").accountType("Savings")
                .balanceDate("04/07/2021").currency("SGD").openingAvailableBalance(345.6).build());
        accountDtos.add(AccountDto.builder().accountNumber("123523").accountName("SGCREDITAC").accountType("Current").build());
        when(mapper.entitiesToDtos(accounts)).thenReturn(accountDtos);
        ApiResponseDto<List<AccountDto>> responseDto = accountService.findAccountsByUserId("1", 0, 2);
        assertEquals("200", responseDto.getMeta().getCode());
        assertEquals("success", responseDto.getMeta().getMessage());
        assertEquals(1, responseDto.getMeta().getTotalPages());
        assertEquals(2, responseDto.getMeta().getTotalItems());
        List<AccountDto> accountList = responseDto.getData();
        assertEquals(2, accountList.size());
        assertEquals("123456", accountList.get(0).getAccountNumber());
        assertEquals("SGDEBITAC", accountList.get(0).getAccountName());
        assertEquals("Savings", accountList.get(0).getAccountType());
        assertEquals("04/07/2021", accountList.get(0).getBalanceDate());
        assertEquals("SGD", accountList.get(0).getCurrency());
        assertEquals(345.6, accountList.get(0).getOpeningAvailableBalance());
    }

    @Test
    public void givenInvalidAccountIdRetrieveTransactionsThrowsException() {
        assertThrows(AccountsException.class, () -> {
           accountService.findTransactionsByAccountNumber("123456",0, 2);
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
                .transactionType("Debit").valueDate(LocalDate.of(2021,07,04)).amount(20.56).build());
        transactions.add(Transaction.builder().id("2").debitAccount(account2).creditAccount(account3)
                .transactionType("Debit").valueDate(LocalDate.of(2021,07,04)).amount(20.56).build());
        when(accountRepository.findById("123456")).thenReturn(Optional.of(account2));
        when(transactionRepository.findAllByCreditAccountOrDebitAccount(account2, account2, PageRequest.of(0, 2))).thenReturn(new PageImpl<>(transactions));
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactionDtos.add(TransactionDto.builder().accountNumber(account2.getAccountNumber())
                .accountName(account2.getAccountNumber()).creditAmount(20.56).debitAmount(0).currency("SGD")
                .valueDate("Jul, 04, 2021").type("Credit").build());
        transactionDtos.add(TransactionDto.builder().accountNumber(account2.getAccountNumber())
                .accountName(account2.getAccountNumber()).debitAmount(20.56).creditAmount(0).currency("SGD")
                .valueDate("Jul, 04, 2021").type("Credit").build());
        when(mapper.transactionEntitiesToDtos(transactions, account2)).thenReturn(transactionDtos);
        ApiResponseDto<List<TransactionDto>> transactionList = accountService.findTransactionsByAccountNumber("123456",0, 2);
        assertEquals("200", transactionList.getMeta().getCode());
        assertEquals("success", transactionList.getMeta().getMessage());
        assertEquals(1, transactionList.getMeta().getTotalPages());
        assertEquals(2, transactionList.getMeta().getTotalItems());
        assertEquals(2, transactionList.getData().size());
    }

}
