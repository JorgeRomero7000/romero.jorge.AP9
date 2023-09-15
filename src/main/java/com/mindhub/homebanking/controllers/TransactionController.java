package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @Transactional
    @PostMapping(path = "/transactions")
    public ResponseEntity<Object> generateTransaction(@RequestParam(name = "fromAccountNumber") String originAccountNumber,
                                                      @RequestParam (name = "toAccountNumber")String destinyAccountNumber,
                                                      @RequestParam double amount,
                                                      @RequestParam String description,
                                                      Authentication authentication){

        if(authentication != null){
            Client client = clientService.findByEmail(authentication.getName());

            if(description.isBlank() || originAccountNumber.isBlank() || destinyAccountNumber.isBlank()){
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            }

            if(amount <= 0){
                return new ResponseEntity<>("You cannot transfer a balance less than 0", HttpStatus.FORBIDDEN);
            }

            if (originAccountNumber.equals(destinyAccountNumber)){
                return new ResponseEntity<>("You cannot transfer to the same originating account", HttpStatus.FORBIDDEN);
            }

            Account destinyAccount = accountService.findByNumber(destinyAccountNumber);

            if(destinyAccount == null){
                return new ResponseEntity<>("Destination account does not exist.", HttpStatus.FORBIDDEN);
            }

            Account originAccount = accountService.findByNumber(originAccountNumber);

            if (!originAccount.getClient().equals(client)){
                return new ResponseEntity<>("You don't have access to this account.", HttpStatus.FORBIDDEN);
            }

            if (originAccount.getBalance() < amount){
                return new ResponseEntity<>("Insufficient funds",HttpStatus.FORBIDDEN);
            }

            String descriptionOrigin = description + " " + destinyAccountNumber;
            String descriptionDestiny = description + " " + originAccountNumber;

            //Transaction originAccountTransaction = transactionService.transactionSave(new Transaction(TransactionType.DEBIT, -amount, descriptionOrigin, LocalDateTime.now()));
            Transaction originAccountTransaction = new Transaction(TransactionType.DEBIT, -amount, descriptionOrigin, LocalDateTime.now());
            originAccount.addTransaction(originAccountTransaction);
            transactionService.transactionSave(originAccountTransaction);

            //Transaction destinyAccountTransaction = transactionService.transactionSave(new Transaction(TransactionType.CREDIT, amount, descriptionDestiny,LocalDateTime.now()));
            Transaction destinyAccountTransaction = new Transaction(TransactionType.CREDIT, amount, descriptionDestiny,LocalDateTime.now());

            destinyAccount.addTransaction(destinyAccountTransaction);
            transactionService.transactionSave(destinyAccountTransaction);

            accountService.accountSave(originAccount);
            accountService.accountSave(destinyAccount);

            return new ResponseEntity<>("Successful transfer", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("You are not logged it", HttpStatus.FORBIDDEN);
    }
}
