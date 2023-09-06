package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private LoanRepository loanRepository;

@GetMapping("/loans")
public List<LoanDTO> getLoans(){
    return loanRepository.findAll()
            .stream()
            .map(LoanDTO::new)
            .collect(Collectors.toList());
}

@Transactional
@RequestMapping(value="/loans", method= RequestMethod.POST)
public ResponseEntity<Object> applyLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
    if (authentication != null){
        Client client = clientRepository.findByEmail(authentication.getName());

        if(loanApplicationDTO.getLoanId() == null || loanApplicationDTO.getToAccountNumber().isBlank()
                    || loanApplicationDTO.getPayments() == 0 || loanApplicationDTO.getAmount() == 0){
                    return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getAmount() <= 0){
            return new ResponseEntity<>("A loan can't be less than or equal to 0.", HttpStatus.FORBIDDEN);
        }
        Account accountTarget = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());
        if (accountTarget == null){
            return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
        }
        if (!accountTarget.getClient().equals(client)){
            return new ResponseEntity<>("You cannot apply for a loan for someone else's account.", HttpStatus.FORBIDDEN);
        }

        Loan loan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);

        if (loan == null){
            return new ResponseEntity<>("Loan does not exist", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("Requested amount exceeds the maximum allowed", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("The requested number of installments is not available.", HttpStatus.FORBIDDEN);
        }

        //requested loan
        double amountLoan = loanApplicationDTO.getAmount() + (loanApplicationDTO.getAmount() * 0.20);
        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getPayments(), amountLoan);
        client.addClientLoan(clientLoan);
        loan.addClientLoan(clientLoan);

        //transaction
        String description = loan.getName() + " Loan approved.";
        Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), description, LocalDateTime.now());
        accountTarget.addTransaction(transaction);
        transactionRepository.save(transaction);
        accountRepository.save(accountTarget);
        clientRepository.save(client);

        return new ResponseEntity<>("A loan has been successfully requested" ,HttpStatus.CREATED);
    }

    return new ResponseEntity<>("You are not logged it", HttpStatus.FORBIDDEN);
    }

}
