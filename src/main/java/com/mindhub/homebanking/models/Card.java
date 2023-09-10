package com.mindhub.homebanking.models;

import com.mindhub.homebanking.repositories.CardRepository;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;
    private String cardHolder, number, cvv;
    private CardType type;
    private CardColor color;
    private LocalDateTime fromDate;
    private LocalDateTime thruDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Card() {
    }

    public Card(String cardHolder, CardType type, CardColor color, String number, String cvv, LocalDateTime fromDate, LocalDateTime thruDate) {
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
    }

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    //******************************************************
    // Se debe mover el método generateCardNumber del modelo
    //******************************************************
    public static String generateCardNumber(){
         String numberCard = "";
            for (int i = 0; i < 4; i++ ) {
                int number = (int)((Math.random() * (9999 - 1000)) + 1000);
                numberCard = numberCard + number;

                if (i < 3) {
                    numberCard+= "-";
                }
            }
        return numberCard;
    }
    public static String generateCvv(){
        Random random = new Random();
        int number;
        number = random.nextInt(999) + 1;
        String cvv;
            cvv = String.format("%03d", number);
        return cvv;
    }
}
