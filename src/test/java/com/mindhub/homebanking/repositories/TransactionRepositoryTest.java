package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;
    // Verifica si existen transacciones
    @Test
    public void existTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, is(not(empty())));
    }

    // Verifica la creación de una nueva transacción
    @Test
    public void newTransaction(){
        Transaction transaction = new Transaction(TransactionType.DEBIT, 75432.0, "debt payment", LocalDateTime.now());
        transactionRepository.save(transaction);
        Transaction transactionSaved = transactionRepository.findById(transaction.getId()).orElse(null);
        assertThat(transactionSaved, equalTo(transaction));

    }



}