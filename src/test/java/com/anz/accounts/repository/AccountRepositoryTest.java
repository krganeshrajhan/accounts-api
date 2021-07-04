package com.anz.accounts.repository;

import com.anz.accounts.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Sql("/create_accounts.sql")
    public void givenUserIdRetrieveAllAccounts() {
        User user = new User();
        user.setUserId("1");
        assertEquals(1, accountRepository.findAllByUser(user, PageRequest.of(0, 2)).toList().size());
    }

}
