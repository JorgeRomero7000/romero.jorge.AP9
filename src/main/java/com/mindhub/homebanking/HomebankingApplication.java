package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository){
		return (args ) -> {

			// Nuevo Cliente 1
			Client client1 = new Client("44555666","Melba","Morel","melba@mindhub.com");
			clientRepository.save(client1);

			// Nuevo Cliente 2
			Client client2 = new Client("42555111","Steve Ray","Vaughan","stivie.ray@mindhub.com");
			clientRepository.save(client2);

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

			// Transacciones para Cliente 1
			LocalDate otherDay = today.plusDays(4);
			Transaction transaction1 = new Transaction(TransactionType.CREDIT,5000,"Primer credito",otherDay,account1);
			transactionRepository.save(transaction1);
			Transaction transaction2 = new Transaction(TransactionType.DEBIT,-1000,"Primer debito",otherDay, account1);
			transactionRepository.save(transaction2);

			// Transacciones para Cliente 2
			Transaction transaction3 = new Transaction(TransactionType.CREDIT,28000,"Primer credito",otherDay,account2);
			transactionRepository.save(transaction3);

			Transaction transaction4 = new Transaction(TransactionType.DEBIT,-3785,"Primer debito",otherDay, account2);
			transactionRepository.save(transaction4);


			Loan loan1 = new Loan("Hipotecario",500000,List.of(12,24,36,48,60));
			loanRepository.save(loan1);
			Loan loan2 = new Loan("Personal",100000, List.of(6,12,24));
			loanRepository.save(loan2);
			Loan loan3 = new Loan("Automotríz",300000, List.of(6,12,24,36));
			loanRepository.save(loan3);

			// Solicitud de préstamo Cliente 1
			ClientLoan clientLoan1 = new ClientLoan(60,400000.0,client1,loan1);
			ClientLoan clientLoan2 = new ClientLoan(12,50000.0,client1,loan2);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);

			// Solicitud de préstamo Cliente 2
			ClientLoan clientLoan3 = new ClientLoan(24,100000.0,client2,loan2);
			ClientLoan clientLoan4 = new ClientLoan(12,50000.0,client2,loan3);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			// Tarjetas
			otherDay = today.plusYears(5);
			Card card1 = new Card(client1.getFirstName()+", "+client1.getLastName(),CardType.DEBIT,CardColor.GOLD,"1122-3344-5566-7788","414",today,otherDay,client1);
			cardRepository.save(card1);

			Card card2 = new Card(client1.getFirstName()+", "+client1.getLastName(),CardType.CREDIT,CardColor.TITANIUM,"7788-6655-4433-0022","969",today,otherDay,client1);
			cardRepository.save(card2);

			Card card3 = new Card(client2.getFirstName()+", "+client2.getLastName(),CardType.CREDIT,CardColor.SILVER,"3434-7878-5665-1187-0632","119",today,otherDay,client2);
			cardRepository.save(card3);

		};
	}
}
