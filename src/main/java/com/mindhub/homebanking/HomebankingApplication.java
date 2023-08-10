package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
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
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args ) -> {

			// Nuevo Cliente 1
			Client client1 = new Client("44555666","Melba","Morel","melba@mindhub.com");
			clientRepository.save(client1);

			// Cuenta VIN001 para Cliente 1
			Account account1 = new Account("VIN001", LocalDate.now(),5000,client1);
			client1.addAccount(account1);
			accountRepository.save(account1);

			LocalDate today = LocalDate.now();
			LocalDate tomorrow = today.plusDays(1);

			// Nueva cuenta VIN002 para cliente 1
			Account account2 = new Account("VIN002",tomorrow,7500,client1);
			client1.addAccount(account2);
			accountRepository.save(account2);

			// Nueva transacción para Cliente 1

			LocalDate otherDay = today.plusDays(4);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT,5000,"Primer credito",otherDay,account1);
			transactionRepository.save(transaction1);

			Transaction transaction2 = new Transaction(TransactionType.DEBIT,-1000,"Primer debito",otherDay, account1);
			transactionRepository.save(transaction2);

		};
	}
}
