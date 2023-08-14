package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientLoanDTO {
    private Long id;
    private Integer payments;
    private Double amount;
    private String name; // Nombre del préstamo
    private Long loanId;

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.payments = clientLoan.getPayments();
        this.amount = clientLoan.getAmount();
        this.name = clientLoan.getLoan().getName();
        this.loanId = clientLoan.getLoan().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }
}
