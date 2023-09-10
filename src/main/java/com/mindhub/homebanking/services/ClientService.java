package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

public interface ClientService {
    //Trae lista de clientes DTO
    List<ClientDTO> getClients();

    // Recupera cliente por Id
    ClientDTO getClientById(@PathVariable Long id);
// Trae cliente autenticado (lo busca por email)
    ClientDTO getCurrentClient(String email);

// Busca al cliente por email
    Client findByEmail (String email);
    // Agrega un nuevo cliente
    void clientAdd(Client client);

    //Almacena al cliente
    void clientSave(Client client);


}
