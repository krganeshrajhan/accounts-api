package com.anz.accounts.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "ACCOUNTS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account implements Serializable {

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

    @Column(name = "BALANCE_DATE", nullable = false)
    private LocalDate balanceDate;

    @Column(name = "CURRENCY", nullable = false)
    private String currency;

    @Column(name = "AVAILABLE_BALANCE", nullable = false)
    private double availableBalance;

    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    @NotNull
    @Size(min = 1, max= 200)
    private User user;
}
