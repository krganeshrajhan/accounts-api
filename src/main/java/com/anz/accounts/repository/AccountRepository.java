package com.anz.accounts.repository;

import com.anz.accounts.model.Account;
import com.anz.accounts.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Page<Account> findAllByUser(User user, Pageable pageable);

}
