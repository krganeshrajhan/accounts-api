package com.anz.accounts.repository;

import com.anz.accounts.model.Account;
import com.anz.accounts.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.List;

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
        assertEquals(1, accountRepository.findAllByUser(user).size());
    }

}
