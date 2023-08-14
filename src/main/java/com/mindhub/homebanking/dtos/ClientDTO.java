package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long Id;
    private String dni;
    private String firstName;
    private String lastName;
    private String email;
    private Set<AccountDTO> accounts = new HashSet<>();
    private Set<ClientLoanDTO> loans = new HashSet<>();


    public ClientDTO(Client client) {
        this.Id = client.getId();
        this.dni = client.getDni();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccount()
                            .stream()
                            .map(account -> new AccountDTO(account))
                            .collect(Collectors.toSet());

        this.loans = client.getLoans()
                        .stream()
                        .map(loan -> new ClientLoanDTO(loan))
                        .collect(Collectors.toSet());


    }

    public Long getId() {
        return Id;
    }

    public String getDni() {
        return dni;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }
}
