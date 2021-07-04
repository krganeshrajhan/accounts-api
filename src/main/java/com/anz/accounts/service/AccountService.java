package com.anz.accounts.service;

import com.anz.accounts.dto.ApiResponseDto;
import com.anz.accounts.dto.ApiResponseMetaDto;
import com.anz.accounts.exception.AccountsException;
import com.anz.accounts.model.Account;
import com.anz.accounts.model.Transaction;
import com.anz.accounts.model.User;
import com.anz.accounts.repository.AccountRepository;
import com.anz.accounts.repository.TransactionRepository;
import com.anz.accounts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public ApiResponseDto<List<Account>> findAccountsByUserId(String userId) throws AccountsException {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            throw new AccountsException("Invalid User ID: " + userId);
        }

        List<Account> accounts = accountRepository.findAllByUser(user.get());
        return getSuccessResponse(accounts, 1, accounts.size());
    }

    public ApiResponseDto<List<Transaction>> findTransactionsByAccountNumber(String accountNumber) throws AccountsException {
        Optional<Account> account = accountRepository.findById(accountNumber);
        if(!account.isPresent()) {
            throw new AccountsException("Invalid Account Number: " + accountNumber);
        }
        List<Transaction> allByCreditAccountOrDebitAccount = transactionRepository.findAllByCreditAccountOrDebitAccount(account.get(), account.get());
        return getSuccessResponse(allByCreditAccountOrDebitAccount, 1, allByCreditAccountOrDebitAccount.size());
    }

    public <T> ApiResponseDto<T> getSuccessResponse(T accounts, int totalPages, long totalItems) {
        return (ApiResponseDto<T>) ApiResponseDto.builder().meta(ApiResponseMetaDto.builder().code(String.valueOf(HttpStatus.OK.value()))
                .message("success").totalPages(totalPages).totalItems(totalItems).build()).data(accounts).build();
    }
}
