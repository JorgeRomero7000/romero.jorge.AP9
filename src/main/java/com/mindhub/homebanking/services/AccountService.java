package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {

    //trae numero de cuenta verificado
    Account findByNumber(String number);

    // Graba una cuenta
    void accountSave(Account account);

    // Recupera lista de Cuentas DTO
    List<AccountDTO> getAccounts();

    //Recupera cuenta por id
    Account findByID (Long id);

    boolean existsByNumber(String number);

}
