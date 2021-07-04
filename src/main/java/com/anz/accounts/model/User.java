package com.anz.accounts.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    @NotNull
    @Size(min = 1, max = 200)
    private String userId;

    @Column(name = "USER_NAME", nullable = false)
    @NotNull
    @Size(min = 1, max = 200)
    private String userName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Account> accountList;
}
