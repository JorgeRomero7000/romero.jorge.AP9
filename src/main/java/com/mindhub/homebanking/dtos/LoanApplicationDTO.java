package com.mindhub.homebanking.dtos;

import java.util.List;

public class LoanApplicationDTO {

    private Long loanId;
    private Integer payments;

    private Double amount;
    private String toAccountNumber;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(Long loanId, Double amount, Integer payments, String toAccountNumber) {
        this.loanId = loanId;
        this.payments = payments;
        this.amount = amount;
        this.toAccountNumber = toAccountNumber;
    }

    public Long getLoanId() {
        return loanId;
    }

    public Integer getPayments() {
        return payments;
    }

    public Double getAmount() {
        return amount;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
