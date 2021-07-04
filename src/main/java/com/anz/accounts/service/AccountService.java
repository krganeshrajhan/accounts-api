package com.anz.accounts.service;

import com.anz.accounts.dto.AccountDto;
import com.anz.accounts.dto.ApiResponseDto;
import com.anz.accounts.dto.ApiResponseMetaDto;
import com.anz.accounts.dto.TransactionDto;
import com.anz.accounts.exception.AccountsException;
import com.anz.accounts.mapper.AccountsMapper;
import com.anz.accounts.model.Account;
import com.anz.accounts.model.Transaction;
import com.anz.accounts.model.User;
import com.anz.accounts.repository.AccountRepository;
import com.anz.accounts.repository.TransactionRepository;
import com.anz.accounts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    private AccountsMapper mapper;

    public ApiResponseDto<List<AccountDto>> findAccountsByUserId(String userId, int pageStart, int pageSize) throws AccountsException {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            throw new AccountsException("Invalid User ID: " + userId);
        }

        Page<Account> pagedAccounts = accountRepository.findAllByUser(user.get(), PageRequest.of(pageStart, pageSize));
        List<AccountDto> accountDtos = mapper.entitiesToDtos(pagedAccounts.getContent());
        return getSuccessResponse(accountDtos, pagedAccounts.getTotalPages(), pagedAccounts.getTotalElements());
    }

    public ApiResponseDto<List<TransactionDto>> findTransactionsByAccountNumber(String accountNumber, int pageStart, int pageSize) throws AccountsException {
        Optional<Account> account = accountRepository.findById(accountNumber);
        if(!account.isPresent()) {
            throw new AccountsException("Invalid Account Number: " + accountNumber);
        }
        Page<Transaction> allByCreditAccountOrDebitAccount = transactionRepository
                .findAllByCreditAccountOrDebitAccount(account.get(), account.get(), PageRequest.of(pageStart, pageSize));
        List<TransactionDto> transactionDtos = mapper.transactionEntitiesToDtos(allByCreditAccountOrDebitAccount.getContent(), account.get());
        return getSuccessResponse(transactionDtos, allByCreditAccountOrDebitAccount.getTotalPages(), allByCreditAccountOrDebitAccount.getTotalElements());
    }

    public <T> ApiResponseDto<T> getSuccessResponse(T accounts, int totalPages, long totalItems) {
        return (ApiResponseDto<T>) ApiResponseDto.builder().meta(ApiResponseMetaDto.builder().code(String.valueOf(HttpStatus.OK.value()))
                .message("success").totalPages(totalPages).totalItems(totalItems).build()).data(accounts).build();
    }
}
