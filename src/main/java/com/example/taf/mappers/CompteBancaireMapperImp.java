package com.example.taf.mappers;

import com.example.taf.dto.ClientDTO;
import com.example.taf.dto.CompteCourantDTO;
import com.example.taf.dto.CompteEpargneDTO;
import com.example.taf.entities.Client;
import com.example.taf.entities.CompteBancaire;
import com.example.taf.entities.CompteCourant;
import com.example.taf.entities.CompteEpargne;
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
    public CompteCourantDTO fromCompteCourant(CompteCourant compteCourant){
          CompteCourantDTO courantDTO= new CompteCourantDTO();
          BeanUtils.copyProperties(compteCourant,courantDTO);
          courantDTO.setClientdto(fromClient(compteCourant.getClient()));
          return courantDTO;
    }

    public CompteCourant fromCompteCourantDTO(CompteCourantDTO compteCourantDTO){
     CompteCourant compteCourant = new CompteCourant();
     BeanUtils.copyProperties(compteCourantDTO,compteCourant);
     compteCourant.setClient(fromClientDTO(compteCourantDTO.getClientdto()));
     return compteCourant;
    }

    public CompteEpargneDTO fromCompteEpargne(CompteEpargne compteEpargne){
CompteEpargneDTO EpargneDTO = new CompteEpargneDTO();
BeanUtils.copyProperties(compteEpargne,EpargneDTO);
EpargneDTO.setClientdto(fromClient(compteEpargne.getClient()));
return EpargneDTO;
    }
    public CompteEpargne fromCompteEpargneDTO(CompteEpargneDTO compteEpargneDTO){

        CompteEpargne compteEpargne = new CompteEpargne();
        BeanUtils.copyProperties(compteEpargneDTO,compteEpargne);
        compteEpargne.setClient(fromClientDTO(compteEpargneDTO.getClientdto()));
        return compteEpargne;
    }
}

