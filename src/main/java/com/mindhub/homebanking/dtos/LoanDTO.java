package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.HashSet;
import java.util.Set;

public class LoanDTO {
    private Long id;
    private String name;
    private Integer maxAmount;
    private Set<ClientDTO> clientDTO = new HashSet<>();
    private Set<ClientLoanDTO> loans = new HashSet<>();

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
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

    public Set<ClientDTO> getClientDTO() {
        return clientDTO;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }
}
