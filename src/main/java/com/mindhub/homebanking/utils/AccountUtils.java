package com.mindhub.homebanking.utils;

import java.util.Random;

public class AccountUtils {

    public static String generateAccountNumber(){
        Random random = new Random();
        int number;
        String numberAccount;
        number = random.nextInt(99999999) + 1;
        numberAccount = "VIN" + number;

        return numberAccount;
    }

}
