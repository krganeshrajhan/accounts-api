package com.anz.accounts.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @Column(name = "ACCOUNT_NUMBER", nullable = false, length = 200)
    private String accountNumber;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private User user;
}
