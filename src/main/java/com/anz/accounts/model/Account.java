package com.anz.accounts.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "ACCOUNTS")
@AllArgsConstructor
@NoArgsConstructor
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
