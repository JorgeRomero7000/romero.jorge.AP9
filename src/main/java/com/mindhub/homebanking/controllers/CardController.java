package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;


    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication, CardColor cardColor, CardType cardType) {

        if (authentication != null) {
            Client client = clientService.findByEmail(authentication.getName());

            if (cardType == null || cardColor == null) {
                return new ResponseEntity<>("Missing data", HttpStatus.NO_CONTENT);
            }


            // Verificación de la cantidad de tarjetas
            Set<Card> cards = client.getCards();
            Set<Card> creditCards = cards.stream().filter(card -> card.getType() == CardType.CREDIT).collect(Collectors.toSet());
            Set<Card> debitCards = cards.stream().filter(card -> card.getType() == CardType.DEBIT).collect(Collectors.toSet());

            // Verificación número máximo tarjetas de crédito
            if (cardType == CardType.CREDIT) {
                // Si el tamaño del "set" es menor que 3...
                if (creditCards.size() < 3) {
                    // si existe una tarjeta de cualquier color...
                    if (creditCards.stream().anyMatch(card -> card.getColor().equals(cardColor))) {
                        return new ResponseEntity<>("You already have such a card", HttpStatus.FORBIDDEN);
                    }
                } else {
                        // se alcanzó el límite de tarjetas de débito permitido
                        return new ResponseEntity<>("You have reached the credit card limit", HttpStatus.FORBIDDEN);
                }
            }
            // Verificación número máximo tarjetas de débito
            if (cardType == CardType.DEBIT) {
                if (debitCards.size() < 3) {
                    if (debitCards.stream().anyMatch(card -> card.getColor().equals(cardColor))) {
                        return new ResponseEntity<>("You already have such a card", HttpStatus.FORBIDDEN);
                    }
                } else {
                    return new ResponseEntity<>("You have reached the debit card limit", HttpStatus.FORBIDDEN);
                }
            }

            // crear tarjeta
            String cardHolder = client.toString();
            //String number = Card.generateCardNumber(cardRepository);
            String number;
            do{
                number = CardUtils.generateCardNumber();
            } while(cardService.existsByNumber(number));

            String cvv;

            do {
                cvv = CardUtils.generateCvv();
            } while(cardService.existsByCvv(cvv));

            LocalDateTime thruDate = LocalDateTime.now().plusYears(5);
            LocalDateTime fromDate = LocalDateTime.now();

            Card card = new Card(cardHolder, cardType, cardColor, number, cvv, fromDate, thruDate);
            client.addCard(card);
            cardService.cardSave(card);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>("You are not logged it", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCurrentAccounts(Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());

        return client.getCards().stream().map(CardDTO::new).collect(toList());
    }


}

