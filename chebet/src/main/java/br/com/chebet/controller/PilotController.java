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

import br.com.chebet.model.Pilot;

import br.com.chebet.service.PilotService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;

@RestController
@RequestMapping("/api/pilot")
public class PilotController {
    
    @Autowired
    PilotService pilotService;

    @PostMapping("/")
    public ResponseEntity<String> registerPilot(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return pilotService.register(requestMap);
        } catch (Exception e) {
            return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Pilot>> getAllPilots() {
        try {
            return pilotService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Pilot>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Pilot> getPilot(@PathVariable int id) {
        try {
            return pilotService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Pilot>(new Pilot(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePilot(@PathVariable int id) {
        try {
            return pilotService.delete(id);
        } catch (Exception e) {
            return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @PutMapping("/")
    public ResponseEntity<String> updatePilot(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return pilotService.update(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/isWorking")
    public ResponseEntity<String> isPilotRepositoryWorking() {
        if(pilotService.isPilotRepositoryWorking()) {
            return ChebetUtils.getResponseEntity("Everything working OK", HttpStatus.OK);
        } else {
            return ChebetUtils.getResponseEntity("Not Working", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
