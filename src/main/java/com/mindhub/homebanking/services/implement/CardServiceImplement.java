package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public void cardSave(Card card) {
        cardRepository.save(card);
    }

    @Override
    public boolean existsByNumber(String number) {
       return cardRepository.existsByNumber(number);
    }

    @Override
    public boolean existsByCvv(String cvv) {
        return cardRepository.existsByCvv(cvv);
    }
}
