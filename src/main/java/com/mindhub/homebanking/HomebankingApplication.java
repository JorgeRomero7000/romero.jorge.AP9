package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Autowired
	PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository){
		return (args ) -> {
/*
			// Creación de Préstamos del banco
			Loan loan1 = new Loan("Hipotecario",500000,List.of(12,24,36,48,60));
			loanRepository.save(loan1);
			Loan loan2 = new Loan("Personal",100000, List.of(6,12,24));
			loanRepository.save(loan2);
			Loan loan3 = new Loan("Automotríz",300000, List.of(6,12,24,36));
			loanRepository.save(loan3);
*/

			/*
			*************************
				C L I E N T E   1
			*************************
			 */

/*
			// Creación Cliente 1
			Client client1 = new Client("Melba","Morel","melba@mindhub.com", passwordEncoder.encode("222"));
			clientRepository.save(client1);

			// Cuenta VIN001 para Cliente 1
			Account account1 = new Account("VIN001", LocalDateTime.now(),5000);
			client1.addAccount(account1);
			accountRepository.save(account1);

			LocalDateTime today = LocalDateTime.now();
		    LocalDateTime tomorrow = today.plusDays(1);
			// Nueva cuenta VIN002 para cliente 1
			Account account2 = new Account("VIN002",tomorrow,7500);
			client1.addAccount(account2);
			accountRepository.save(account2);

			// Transacciones para Cliente 1
			LocalDateTime otherDay = today.plusDays(4);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT,5000,"Primer credito",today);
			account1.addTransaction(transaction1);
			transactionRepository.save(transaction1);
			Transaction transaction2 = new Transaction(TransactionType.DEBIT,-1000,"Primer debito",today);
			account1.addTransaction(transaction2);
			transactionRepository.save(transaction2);

			otherDay = today.plusDays(1);
			Transaction transaction5 = new Transaction(TransactionType.CREDIT,3500,"Primer credito",otherDay);
			account2.addTransaction(transaction5);
			transactionRepository.save(transaction5);
			Transaction transaction6 = new Transaction(TransactionType.DEBIT,-2900,"Primer debito",otherDay);
			account2.addTransaction(transaction6);
			transactionRepository.save(transaction6);


			// Solicitud de préstamo Cliente 1
			ClientLoan clientLoan1 = new ClientLoan(60,400000.0);
			client1.addClientLoan(clientLoan1);
			loan1.addClientLoan(clientLoan1);
			clientLoanRepository.save(clientLoan1);

			ClientLoan clientLoan2 = new ClientLoan(12,50000.0);
			client1.addClientLoan(clientLoan2);
			loan1.addClientLoan(clientLoan2);
			clientLoanRepository.save(clientLoan2);
*/
//***********************************************************************************
			/*
			*************************
				C L I E N T E   2
			*************************
			 */
/*
			// Nuevo Cliente 2
			Client client2 = new Client("Jorge","Romero","aa@aa.com", passwordEncoder.encode("111"));
			clientRepository.save(client2);

			// Cuenta VIN003 para Cliente 2
			Account account3 = new Account("VIN003", LocalDateTime.now(),5000);
			client2.addAccount(account3);
			accountRepository.save(account3);

			// Nueva cuenta VIN004 para cliente 2
			Account account4 = new Account("VIN004",tomorrow,7500);
			client2.addAccount(account4);
			accountRepository.save(account4);

			// Transacciones para Cliente 2
			Transaction transaction3 = new Transaction(TransactionType.CREDIT,28000,"Primer credito",otherDay);
			account3.addTransaction(transaction3);
			transactionRepository.save(transaction3);

			Transaction transaction4 = new Transaction(TransactionType.DEBIT,-3785,"Primer debito",otherDay);
			account4.addTransaction(transaction4);
			transactionRepository.save(transaction4);

			// Solicitud de préstamo Cliente 2
			ClientLoan clientLoan3 = new ClientLoan(24,100000.0);
			client2.addClientLoan(clientLoan3);
			loan2.addClientLoan(clientLoan3);
			clientLoanRepository.save(clientLoan3);

			ClientLoan clientLoan4 = new ClientLoan(12,50000.0);
			client2.addClientLoan(clientLoan4);
			loan3.addClientLoan(clientLoan4);
			clientLoanRepository.save(clientLoan4);

/*
		// Tarjetas: Creación manual de tarjetas. Luego, fue reemplazado por programación.
			otherDay = today.plusYears(5);
			Card card1 = new Card(client1.getFirstName()+", "+client1.getLastName(),CardType.DEBIT,CardColor.GOLD,"1122-3344-5566-7788","414",today,otherDay);
			cardRepository.save(card1);

			Card card2 = new Card(client1.getFirstName()+", "+client1.getLastName(),CardType.CREDIT,CardColor.TITANIUM,"7788-6655-4433-0022","969",today,otherDay);
			cardRepository.save(card2);

			Card card3 = new Card(client2.getFirstName()+", "+client2.getLastName(),CardType.CREDIT,CardColor.SILVER,"3434-7878-5665-1187-0632","119",today,otherDay);
			cardRepository.save(card3);
*/

		};
	}
}
