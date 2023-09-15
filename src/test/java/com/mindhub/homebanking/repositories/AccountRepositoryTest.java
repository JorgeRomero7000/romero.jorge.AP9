package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.time.LocalDateTime;
import java.util.List;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)

public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    // Verifica que exista al menos una cuenta
    @Test
    public void existAccount(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, is(not(empty())));
    }

    // Verifica la creación y persistencia de una cuenta
    @Test
    public void newAccount(){
        Account account = new Account("VIN-4444", LocalDateTime.now(), 0.0);
        accountRepository.save(account);
        Account accountSaved = accountRepository.findByNumber(account.getNumber());
        assertThat(accountSaved, equalTo(account));
    }

}
