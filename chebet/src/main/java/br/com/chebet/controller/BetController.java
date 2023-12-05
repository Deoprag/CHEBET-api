package br.com.chebet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.chebet.model.Bet;
import br.com.chebet.service.BetService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;

@RestController
@RequestMapping("/api/bet")
public class BetController {

    @Autowired
    BetService betService;

    @PostMapping("/")
    public ResponseEntity<String> registerBet(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return betService.register(requestMap);
        } catch (Exception e) {
            return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByChampionship/{id}")
    public ResponseEntity<List<Bet>> getBetsByChampionship(@PathVariable int id) {
        try {
            return betService.findAllByChampionship(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Bet>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/findByUser/{id}")
    public ResponseEntity<List<Bet>> findByUser(@PathVariable int id) {
        try {
            return betService.findAllByUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Bet>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBet(@PathVariable int id) {
        try {
            return betService.delete(id);
        } catch (Exception e) {
            return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @PutMapping("/")
    public ResponseEntity<String> updateBet(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return betService.update(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
