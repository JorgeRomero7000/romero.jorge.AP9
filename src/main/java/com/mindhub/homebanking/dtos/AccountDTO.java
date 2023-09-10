package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private Long id;
    private String number;
    private LocalDateTime date;
    private double balance;
    private Set<TransactionDTO> transactions = new HashSet<>();

    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        date = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransaction()
                            .stream()
                            .map(transaction -> new TransactionDTO(transaction))
                            .collect(Collectors.toSet());
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {return date;}

    public double getBalance() {
        return balance;
    }
}
