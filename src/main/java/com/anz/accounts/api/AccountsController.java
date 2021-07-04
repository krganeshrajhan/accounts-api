package com.anz.accounts.api;

import com.anz.accounts.dto.ApiResponseDto;
import com.anz.accounts.exception.AccountsException;
import com.anz.accounts.model.Account;
import com.anz.accounts.model.Transaction;
import com.anz.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/accounts/{userId}", produces = APPLICATION_JSON_VALUE)
    public ApiResponseDto<List<Account>> findAccountsByUserId(@PathVariable(value = "userId", required = true) String userId) throws AccountsException {
        return accountService.findAccountsByUserId(userId);
    }

    @GetMapping(value = "/transactions/{accountNumber}", produces = APPLICATION_JSON_VALUE)
    public ApiResponseDto<List<Transaction>> findTransactionsByAccountNumber(@PathVariable("accountNumber") String accountNumber) throws AccountsException {
        return accountService.findTransactionsByAccountNumber(accountNumber);
    }
}
