package br.com.chebet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.chebet.model.Race;
import br.com.chebet.service.RaceService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/race")
public class RaceController {

    @Autowired
    RaceService raceService;

    @GetMapping("/")
    public ResponseEntity<List<Race>> findAll() {
        try {
            return raceService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Race>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/")
    public ResponseEntity<String> updateRace(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return raceService.update(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/findByChampionship/{id}")
    public ResponseEntity<List<Race>> findByChampionship(@PathVariable int id) {
        try {
            return raceService.findByChampionship(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Race>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
