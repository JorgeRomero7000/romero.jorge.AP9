package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.services.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CardUtilsTest {

    @Autowired
    private CardService cardService;
    // Verifica si se retorna una cadena (número de tarjeta) que NO ESTÉ VACÍA
    @Test
    void generateCardNumber() {
        String cardNumber = CardUtils.generateCardNumber();
        assertThat(cardNumber, is(not(emptyOrNullString())));
    }

    // Verifica si se retorna una cadena (código de seguridad) que NO ESTÉ VACÍA
    @Test
    void generateCvv() {
        String cardCVV = CardUtils.generateCvv();
        assertThat(cardCVV, is(not(emptyOrNullString())));
    }
}