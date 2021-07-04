package com.anz.accounts.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "TRANSACTIONS")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, length = 200)
    private String id;

    @Column(name = "VALUE_DATE")
    private LocalDate valueDAte;

    @Column(name = "AMOUNT")
    private double amount;

    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;

    @Column(name = "NARRATIVE")
    private String narrative;

    @JoinColumn(name = "DEBIT_ACCOUNT", nullable = false)
    @ManyToOne(optional = false)
    private Account debitAccount;

    @JoinColumn(name = "CREDIT_ACCOUNT", nullable = false)
    @ManyToOne(optional = false)
    private Account creditAccount;

}
