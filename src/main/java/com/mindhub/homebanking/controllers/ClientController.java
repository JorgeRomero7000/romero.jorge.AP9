package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
/*
     Fecha: 16/08/2023
     ************************************************************************************************
     NOTA: Las líneas comentadas de este bloque, fueron reemplazadas por una única línea de código
            dadas en el "return", lo que hace un código más eficiente al evitar la definición de
            dos variables y ahorrar líneas de código.
    ************************************************************************************************

        List<Client> allClients = clientRepository.findAll();
        List<ClientDTO> convertedList = allClients
                                            .stream()
                                            .map(currentClient -> new ClientDTO(currentClient))
                                            .collect(Collectors.toList());
        return convertedList;
 */
        return clientService.getClients();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {
      //  Optional<Client> clientOptional = clientRepository.findById(id);
        return clientService.getClientById(id);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        return clientService.getCurrentClient(authentication.getName());
    }

    // Creación de un nuevo cliente
    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }else {
            Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));

        //Generación de un número aleatorio para la cuenta nueva
        String numAccount;

        do {
            Random random = new Random();
            numAccount = "VIN-" + random.nextInt(90000000);
        } while (accountService.findByNumber(numAccount) != null);

        // Se crea una cuenta nueva
        // Se agrega al cliente
        // Se guardan los nuevos registros.
        Account account = new Account(numAccount, LocalDateTime.now(), 0.0);
        client.addAccount(account);
        clientService.clientAdd(client);
        accountService.accountSave(account);
    }









        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}




