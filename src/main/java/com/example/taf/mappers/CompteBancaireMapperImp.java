package com.example.taf.mappers;

import com.example.taf.dto.ClientDTO;
import com.example.taf.dto.CompteCourantDTO;
import com.example.taf.dto.CompteEpargneDTO;
import com.example.taf.dto.OperationsDTO;
import com.example.taf.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CompteBancaireMapperImp {

    public ClientDTO fromClient(Client client){
        if (client == null) {
        return null;
    }
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
          courantDTO.setType(compteCourant.getClass().getSimpleName());
          courantDTO.setDevise(compteCourant.getDevise());
          return courantDTO;
    }

    public CompteCourant fromCompteCourantDTO(CompteCourantDTO compteCourantDTO){
     CompteCourant compteCourant = new CompteCourant();
     BeanUtils.copyProperties(compteCourantDTO,compteCourant);
     compteCourant.setClient(fromClientDTO(compteCourantDTO.getClientdto()));
        compteCourant.setDevise(compteCourantDTO.getDevise());
     return compteCourant;
    }

    public CompteEpargneDTO fromCompteEpargne(CompteEpargne compteEpargne){
CompteEpargneDTO EpargneDTO = new CompteEpargneDTO();
BeanUtils.copyProperties(compteEpargne,EpargneDTO);
EpargneDTO.setClientdto(fromClient(compteEpargne.getClient()));
EpargneDTO.setType(compteEpargne.getClass().getSimpleName());
        EpargneDTO.setDevise(compteEpargne.getDevise());

return EpargneDTO;
    }
    public CompteEpargne fromCompteEpargneDTO(CompteEpargneDTO compteEpargneDTO){

        CompteEpargne compteEpargne = new CompteEpargne();
        BeanUtils.copyProperties(compteEpargneDTO,compteEpargne);
        compteEpargne.setClient(fromClientDTO(compteEpargneDTO.getClientdto()));
        compteEpargne.setDevise(compteEpargneDTO.getDevise());
        return compteEpargne;
    }

    public OperationsDTO fromOperation(Operation operation){
        OperationsDTO operationsDTO = new OperationsDTO();
        BeanUtils.copyProperties(operation,operationsDTO);
        return operationsDTO;
    }

    public Operation fromOperationDTO(OperationsDTO operationsDTO){
        Operation operation = new Operation();
        BeanUtils.copyProperties(operationsDTO,operation);
        return operation;
    }


}

