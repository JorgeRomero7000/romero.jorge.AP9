package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return (args ) -> {
			Client client = new Client("44555666","Melba","Morel","melba@mindhub.com");
			clientRepository.save(client);

			Account account1 = new Account("VIN001", LocalDate.now(),5000,client);
			client.addAccount(account1);

			accountRepository.save(account1);

			LocalDate today = LocalDate.now();
			LocalDate tomorrow = today.plusDays(1);

			Account account2 = new Account("VIN002",tomorrow,7500,client);
			client.addAccount(account2);

			accountRepository.save(account2);

		};

	}



}
