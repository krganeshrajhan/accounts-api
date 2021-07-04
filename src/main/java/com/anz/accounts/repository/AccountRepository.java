package com.anz.accounts.repository;

import com.anz.accounts.model.Account;
import com.anz.accounts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    List<Account> findAllByUser(User user);

}
