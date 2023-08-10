package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class TransactionDTO {
    private Long Id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDate date;

    public TransactionDTO(Transaction transaction) {
        this.Id=transaction.getId();
        this.type= transaction.getType();
        this.amount = transaction.getAmount();
        this.description=transaction.getDescription();
        this.date=transaction.getDate();
    }

    public Long getId() {
        return Id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }
}
