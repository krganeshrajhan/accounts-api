package com.anz.accounts.mapper;

import com.anz.accounts.dto.AccountDto;
import com.anz.accounts.dto.TransactionDto;
import com.anz.accounts.model.Account;
import com.anz.accounts.model.Transaction;
import com.anz.accounts.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountsMapperTest {

    AccountsMapper mapper = Mappers.getMapper(AccountsMapper.class);

    @Test
    public void givenEmptyAccountListReturnEmptyDto() {
        List<AccountDto> accountDtos = mapper.entitiesToDtos(new ArrayList<>());
        assertEquals(0, accountDtos.size());
    }

    @Test
    public void givenAccountEntityListReturnAccountDtoList() {
        Account account = Account.builder().user(User.builder().userId("1").userName("name").build()).accountNumber("123456")
                .accountName("SGACC").accountType("Savings").balanceDate(LocalDate.of(2021,07,04)).currency("SGD")
                .availableBalance(2345.68).build();
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        List<AccountDto> accountDtos = mapper.entitiesToDtos(accounts);
        assertEquals(1, accountDtos.size());
        assertEquals("123456", accountDtos.get(0).getAccountNumber());
        assertEquals("SGACC", accountDtos.get(0).getAccountName());
        assertEquals("04/07/2021", accountDtos.get(0).getBalanceDate());
        assertEquals("SGD", accountDtos.get(0).getCurrency());
        assertEquals(2345.68, accountDtos.get(0).getOpeningAvailableBalance());
    }

    @Test
    public void givenEmptyTransactionListReturnEmptyDto() {
        List<TransactionDto> transactionDtos = mapper.transactionEntitiesToDtos(new ArrayList<>(), Account.builder().build());
        assertEquals(0, transactionDtos.size());
    }

    @Test
    public void givenValidTransationAccountReturnTransactionDto() {
        User user = User.builder().userId("1").userName("user").build();
        Account account1 = Account.builder().accountNumber("123456").accountName("SGDEBITAC").accountType("Savings")
                .user(user).build();
        Account account2 = Account.builder().accountNumber("123523").accountName("SGCREDITAC").accountType("Current")
                .currency("SGD")
                .user(user).build();
        Account account3 = Account.builder().accountNumber("123412").accountName("SGCREDITAC").accountType("Savings")
                .user(user).build();
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(Transaction.builder().id("1").debitAccount(account1).creditAccount(account2)
                .transactionType("Debit").valueDate(LocalDate.of(2021,07,04)).amount(20.56).build());
        transactions.add(Transaction.builder().id("2").debitAccount(account2).creditAccount(account3)
                .transactionType("Debit").valueDate(LocalDate.of(2021,07,04)).amount(20.56).build());
        List<TransactionDto> transactionDtos = mapper.transactionEntitiesToDtos(transactions, account2);
        assertEquals(2, transactionDtos.size());
        assertEquals("123523", transactionDtos.get(0).getAccountNumber());
        assertEquals("SGCREDITAC", transactionDtos.get(0).getAccountName());
        assertEquals("Jul, 04, 2021", transactionDtos.get(0).getValueDate());
        assertEquals("SGD", transactionDtos.get(0).getCurrency());
        assertEquals(0, transactionDtos.get(0).getDebitAmount());
        assertEquals(20.56, transactionDtos.get(0).getCreditAmount());
        assertEquals("Credit", transactionDtos.get(0).getType());
        assertEquals(null, transactionDtos.get(0).getNarrative());
        assertEquals("123523", transactionDtos.get(1).getAccountNumber());
        assertEquals("SGCREDITAC", transactionDtos.get(1).getAccountName());
        assertEquals("Jul, 04, 2021", transactionDtos.get(1).getValueDate());
        assertEquals("SGD", transactionDtos.get(1).getCurrency());
        assertEquals(20.56, transactionDtos.get(1).getDebitAmount());
        assertEquals(0, transactionDtos.get(1).getCreditAmount());
        assertEquals("Debit", transactionDtos.get(1).getType());
        assertEquals(null, transactionDtos.get(1).getNarrative());

    }

}
