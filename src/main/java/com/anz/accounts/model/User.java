package com.anz.accounts.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private String userId;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Account> accountList;
}
