package com.anz.accounts.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AccountDto {
    private String accountNumber;
    private String accountName;
    private String accountType;
    private String balanceDate;
    private String currency;
    private double openingAvailableBalance;

}
