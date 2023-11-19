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

import br.com.chebet.model.Preparer;
import br.com.chebet.model.Preparer;
import br.com.chebet.service.PreparerService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;

@RestController
@RequestMapping("/api/preparer")
public class PreparerController {
    @Autowired
    PreparerService preparerService;

    @PostMapping("/")
    public ResponseEntity<String> registerPreparer(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return preparerService.register(requestMap);
        } catch (Exception e) {
            return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Preparer>> getAllPreparers() {
        try {
            return preparerService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Preparer>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Preparer> getPreparer(@PathVariable int id) {
        try {
            return preparerService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Preparer>(new Preparer(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePreparer(@PathVariable int id) {
        try {
            return preparerService.delete(id);
        } catch (Exception e) {
            return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @PutMapping("/")
    public ResponseEntity<String> updatePreparer(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return preparerService.update(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/findByTeam/{id}")
    public ResponseEntity<List<Preparer>> findByTeam(@PathVariable int id) {
        try {
            return preparerService.findByTeam(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Preparer>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
