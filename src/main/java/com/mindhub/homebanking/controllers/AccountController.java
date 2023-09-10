package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    // Recupera lista de cuentas DTO
    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAccounts();
    }

    @RequestMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable Long id, Authentication authentication){

        if (authentication != null) {
            Client client = clientService.findByEmail(authentication.getName());
            Account account = accountService.findByID(id);

            if (account == null) {
                return new ResponseEntity<>("This account does not exist", HttpStatus.NOT_FOUND);
            }

            if (account.getClient().equals(client)) {
                AccountDTO accountDTO = new AccountDTO(account);
                return new ResponseEntity<>(accountDTO, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Access not permited to this account", HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>("You are not logged it", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getAccounts(Authentication authentication) {
        // Busca el cliente autenticado
        Client client = clientService.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
    }

    // Creación de una cuenta nueva
    // Cuando se hace una petición sobre el cliente actual o loggeado...

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication){

        // Si el cliente existe...
        if (authentication != null){
           // Crea una variable "client" que contiene el email del nombre del cliente autenticado
            Client client = clientService.findByEmail(authentication.getName());
            // Crea un set de cuentas con las cuentas del cliente
            Set<Account> accounts = client.getAccounts();

            // Si el "set" tiene más de 3 cuentas, responde con un mensaje de error y un estado
            if(accounts.size() > 3){
                return new ResponseEntity<>("Account limit reached", HttpStatus.CONFLICT);
            }
            // crea una variable de fecha y guarda la fecha actual
            LocalDateTime date = LocalDateTime.now();
            // Crea una variable tipo String y le asigna el valor que devuelve el método "gnerateAccountNumber" de la
            // clase Account (el String con prefijo y número aleatorio de la cuenta)

            String numberAccount;
            do {
                numberAccount = Account.generateAccountNumber();
            }while(accountService.existsByNumber(numberAccount));



            // Crea una variable "balance" tipo double y la inicializa en CERO
            double balance = 0;

            // Crea una variable tipo Account (llamada "account) y le pasa como parámetros del constructor
            // el número de cuenta aleatorio generado, la fecha actual y el valor del balance inicializado
            Account account = new Account(numberAccount,date,balance);
            // Agrega la cuenta al cliente (método addAccount en clase "Client" y le pasa la cuenta recién creada
            client.addAccount(account);
            // A través del repositorio de account, graba los datos de la cuenta (crea un registro en la tabla Account)
            accountService.accountSave(account);
            // Devuelve la petición a través de una nueva respuesta, donde pasa el estado "creado"
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        // Si no ecuentra al cliente, es porque no hay un cliente loggeado, responde con un mje y un estado prohibido
        return new ResponseEntity<>("You are not logged it", HttpStatus.FORBIDDEN);
    }
}
