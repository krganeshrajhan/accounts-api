package com.anz.accounts.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "ACCOUNTS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    @Column(name = "ACCOUNT_NUMBER", nullable = false, length = 200)
    @NotNull
    @Size(min = 1, max= 200)
    private String accountNumber;

    @Column(name = "ACCOUNT_NAME", nullable = false, length = 100)
    @NotNull
    @Size(min = 1, max= 100)
    private String accountName;

    @Column(name = "ACCOUNT_TYPE", nullable = false, length = 50)
    @NotNull
    @Size(min = 1, max= 50)
    private String accountType;

    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    @NotNull
    @Size(min = 1, max= 200)
    private User user;
}
