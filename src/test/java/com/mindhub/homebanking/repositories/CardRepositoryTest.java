package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)

class CardRepositoryTest {

    @Autowired
    CardRepository cardRepository;

    @Autowired
   ClientRepository clientRepository;


/*    @Test
    void existsByNumber(String cardNumber) {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, is(not(empty())));
    }*/
    // Verifica la existencia de al menos una tarjeta
    @Test
    public void existCard(){
        List<Card> cards =cardRepository.findAll();
        assertThat(cards, is(not(empty())));
    }

    // Crea un nuevo cliente
    // Crea una tarjeta
    // Verifica que la tarjeta quede persistida
    @Test
    public void newCard(){
        Client client = new Client("Walter", "Gauna", "wgauna@email.com", "my-Password");
        clientRepository.save(client);
        Card card = new Card(client.toString(), CardType.CREDIT, CardColor.TITANIUM, "8765-1425-3579-1590", "493", LocalDateTime.now().plusYears(5), LocalDateTime.now());
        cardRepository.save(card);
        Card cardSaved = cardRepository.findById(card.getId()).orElse(null);
        assertThat(cardSaved, equalTo(card));
    }
}