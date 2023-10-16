package br.com.chebet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.chebet.service.BetService;
import br.com.chebet.utils.ChebetUtils;

@RestController
@RequestMapping("/api/bet")
public class BetController {

    @Autowired
    BetService betService;

    @GetMapping("/isWorking")
    public ResponseEntity<String> isBetRepositoryWorking() {
        if(betService.isBetRepositoryWorking()) {
            return ChebetUtils.getResponseEntity("Everything working OK", HttpStatus.OK);
        } else {
            return ChebetUtils.getResponseEntity("Not Working", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
