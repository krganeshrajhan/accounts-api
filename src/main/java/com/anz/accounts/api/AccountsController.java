package com.anz.accounts.api;

import com.anz.accounts.dto.ApiResponseDto;
import com.anz.accounts.dto.ApiResponseMetaDto;
import com.anz.accounts.exception.AccountsException;
import com.anz.accounts.model.Account;
import com.anz.accounts.model.Transaction;
import com.anz.accounts.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/accounts/{userId}", produces = APPLICATION_JSON_VALUE)
    public ApiResponseDto findAccountsByUserId(@PathVariable(value = "userId", required = true) String userId) {
        log.info("Processing Api call to findAccountsByUserId: " + userId);
        try {
            return accountService.findAccountsByUserId(userId);
        } catch (AccountsException e) {
            log.error("Exception occured while processing: " + e);
            return ApiResponseDto.builder().meta(ApiResponseMetaDto.builder()
                    .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                    .message(e.getMessage()).build()).build();
        }
    }

    @GetMapping(value = "/transactions/{accountNumber}", produces = APPLICATION_JSON_VALUE)
    public ApiResponseDto findTransactionsByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        log.info("Processing Api call to findTransactionsByAccountNumber: " + accountNumber);
        try {
            return accountService.findTransactionsByAccountNumber(accountNumber);
        } catch (AccountsException e) {
            log.error("Exception occured while processing: " + e);
            return ApiResponseDto.builder().meta(ApiResponseMetaDto.builder()
                    .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                    .message(e.getMessage()).build()).build();
        }
    }
}
