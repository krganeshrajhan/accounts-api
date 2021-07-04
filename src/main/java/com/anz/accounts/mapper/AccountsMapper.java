package com.anz.accounts.mapper;

import com.anz.accounts.dto.AccountDto;
import com.anz.accounts.dto.TransactionDto;
import com.anz.accounts.model.Account;
import com.anz.accounts.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AccountsMapper {

    @Mappings({
            @Mapping(target="balanceDate", source="balanceDate", dateFormat = "dd/MM/yyyy"),
            @Mapping(target = "openingAvailableBalance", source = "availableBalance")
    })
    public abstract AccountDto entityToDto(Account account);

    public abstract List<AccountDto> entitiesToDtos(List<Account> accounts);

    @Mappings({
            @Mapping(target = "accountNumber", source = "account.accountNumber"),
            @Mapping(target = "valueDate", source = "transaction.valueDate", dateFormat = "MMM, dd, yyyy"),
            @Mapping(target = "debitAmount", expression="java((transaction.getDebitAccount().getAccountNumber().equalsIgnoreCase(account.getAccountNumber())) ? transaction.getAmount() : 0d)"),
            @Mapping(target = "creditAmount", expression="java((transaction.getCreditAccount().getAccountNumber().equalsIgnoreCase(account.getAccountNumber())) ? transaction.getAmount() : 0d)"),
            @Mapping(target = "type", expression="java((transaction.getDebitAccount().getAccountNumber().equalsIgnoreCase(account.getAccountNumber())) ? \"Debit\" : \"Credit\")")


    })
    public abstract TransactionDto transactionEntityToDto(Transaction transaction, Account account);

    public List<TransactionDto> transactionEntitiesToDtos(List<Transaction> transactions, Account account) {
        if ( transactions == null ) {
            return null;
        }

        List<TransactionDto> list = new ArrayList<TransactionDto>( transactions.size() );
        for ( Transaction transaction : transactions ) {
            list.add( transactionEntityToDto( transaction, account ) );
        }

        return list;
    }

}
