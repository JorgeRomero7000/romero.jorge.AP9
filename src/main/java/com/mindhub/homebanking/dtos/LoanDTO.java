package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoanDTO {
    private Long id;
    private String name;
    private Integer maxAmount;
    private List<Integer> payments = new ArrayList<>();

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
