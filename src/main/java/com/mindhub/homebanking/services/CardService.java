package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface CardService {

    // Graba una tarjeta
    void cardSave(Card card);

    boolean existsByNumber(String number);

    boolean existsByCvv(String cvv);

}
