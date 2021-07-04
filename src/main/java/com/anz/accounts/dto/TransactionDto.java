package com.anz.accounts.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TransactionDto {
    private String accountNumber;
    private String accountName;
    private String valueDate;
    private String currency;
    private double debitAmount;
    private double creditAmount;
    private String type;
    private String narrative;
}
