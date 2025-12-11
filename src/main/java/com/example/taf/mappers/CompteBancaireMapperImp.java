package com.example.taf.mappers;

import com.example.taf.dto.ClientDTO;
import com.example.taf.entities.Client;
import com.example.taf.entities.CompteBancaire;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CompteBancaireMapperImp {

    public ClientDTO fromClient(Client client){
        ClientDTO clientDTO = new ClientDTO();
       BeanUtils.copyProperties(client,clientDTO);
        return clientDTO;
    }

    public Client fromClientDTO(ClientDTO clientDTO)
    {
       Client client = new Client();
       BeanUtils.copyProperties(clientDTO,client);

        return client;
    }
}
