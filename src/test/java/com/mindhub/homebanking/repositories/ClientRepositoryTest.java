package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)

class ClientRepositoryTest {

    @Autowired ClientRepository clientRepository;

    // Verifica si existe el cliente
    @Test
    public void existClients(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, is(not(empty())));
    }

    // Verifica la creación de un cliente
    @Test
    public void newClient(){
        Client client = new Client("Susana", "Jurado", "susyjurado@aa.com", "pass-de-susy");
        clientRepository.save(client);
        Client clientSaved = clientRepository.findById(client.getId()).orElse(null);
        assertThat(clientSaved, equalTo(client));
    }
}