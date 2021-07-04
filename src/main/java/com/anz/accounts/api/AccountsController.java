package com.anz.accounts.api;

import com.anz.accounts.dto.ApiResponseDto;
import com.anz.accounts.dto.ApiResponseMetaDto;
import com.anz.accounts.exception.AccountsException;
import com.anz.accounts.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/accounts/{userId}", produces = APPLICATION_JSON_VALUE)
    public ApiResponseDto findAccountsByUserId(@PathVariable(value = "userId", required = true) String userId,
            @RequestParam(value = "pageStart", required = false, defaultValue = "0") int pageStart,
            @RequestParam(value = "pageSize", required = false, defaultValue = "250") @Min(1) @Max(250) int pageSize) {
        log.info("Processing Api call to findAccountsByUserId: " + userId);
        try {
            return accountService.findAccountsByUserId(userId, pageStart, pageSize);
        } catch (AccountsException e) {
            log.error("Exception occured while processing: " + e);
            return ApiResponseDto.builder().meta(ApiResponseMetaDto.builder()
                    .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                    .message(e.getMessage()).build()).build();
        }
    }

    @GetMapping(value = "/transactions/{accountNumber}", produces = APPLICATION_JSON_VALUE)
    public ApiResponseDto findTransactionsByAccountNumber(@PathVariable("accountNumber") String accountNumber,
              @RequestParam(value = "pageStart", required = false, defaultValue = "0") int pageStart,
              @RequestParam(value = "pageSize", required = false, defaultValue = "250") @Min(1) @Max(250) int pageSize) {
        log.info("Processing Api call to findTransactionsByAccountNumber: " + accountNumber);
        try {
            return accountService.findTransactionsByAccountNumber(accountNumber, pageStart, pageSize);
        } catch (AccountsException e) {
            log.error("Exception occured while processing: " + e);
            return ApiResponseDto.builder().meta(ApiResponseMetaDto.builder()
                    .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                    .message(e.getMessage()).build()).build();
        }
    }
}
