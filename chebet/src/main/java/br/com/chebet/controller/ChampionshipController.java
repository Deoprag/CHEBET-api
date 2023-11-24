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

import br.com.chebet.model.Championship;
import br.com.chebet.service.ChampionshipService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;

@RestController
@RequestMapping("/api/championship")
public class ChampionshipController {

    @Autowired
    ChampionshipService championshipService;

    @PostMapping("/")
    public ResponseEntity<String> registerChampionship(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return championshipService.register(requestMap);
        } catch (Exception e) {
            return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Championship>> getAllChampionships() {
        try {
            return championshipService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Championship>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Championship> getChampionship(@PathVariable int id) {
        try {
            return championshipService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Championship>(new Championship(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChampionship(@PathVariable int id) {
        try {
            return championshipService.delete(id);
        } catch (Exception e) {
            return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @PutMapping("/")
    public ResponseEntity<String> updateChampionship(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return championshipService.update(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
