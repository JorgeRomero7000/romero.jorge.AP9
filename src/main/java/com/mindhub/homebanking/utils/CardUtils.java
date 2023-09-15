package com.mindhub.homebanking.utils;

import java.util.Random;

public class CardUtils {

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
