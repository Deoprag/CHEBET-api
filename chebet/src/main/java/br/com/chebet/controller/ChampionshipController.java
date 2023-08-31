package br.com.chebet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.chebet.service.ChampionshipService;

@RestController
@RequestMapping("/api/championship")
public class ChampionshipController {

    @Autowired
    ChampionshipService championshipService;

    @GetMapping("/isWorking")
    public ResponseEntity<String> isChampionshipRepositoryWorking() {
        if(championshipService.isChampionshipRepositoryWorking()) {
            return new ResponseEntity<String>("Everything working OK", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Not Working", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
