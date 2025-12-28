package com.example.taf.web;

import com.example.taf.dto.ClientDTO;
import com.example.taf.dto.DashboardStat;
import com.example.taf.entities.Client;
import com.example.taf.services.CompteBancaireServiceRepo;
import com.example.taf.services.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class ClientRestController {
    private CompteBancaireServiceRepo compteBancaireServiceRepo;

    @GetMapping({"/clients","/"})
    public List<ClientDTO> listClients() {
        return compteBancaireServiceRepo.listClients();
    }

    @GetMapping({"/clients/search"})
    public List<ClientDTO> SearchClient(@RequestParam(name = "motcle",defaultValue = "") String motcle) {
        return compteBancaireServiceRepo.searchClient(motcle);
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable(name = "id") String clientId) {
        return compteBancaireServiceRepo.getClient(clientId);

    }
    @PostMapping("/clients")
    public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
        return compteBancaireServiceRepo.saveClient(clientDTO);
    }
    @PutMapping("/clients/{clientId}")
    public ClientDTO updateClient(@PathVariable String clientId,@RequestBody ClientDTO clientDTO) {
        clientDTO.setId(clientId);
        return compteBancaireServiceRepo.UpdateClient(clientDTO);

    }

    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable String id) {
        compteBancaireServiceRepo.deleteClient(id);
    }
}

