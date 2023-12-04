package br.com.chebet.serviceImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Car;
import br.com.chebet.model.Preparer;
import br.com.chebet.model.Race;
import br.com.chebet.repository.RaceRepository;
import br.com.chebet.service.RaceService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RaceServiceImpl implements RaceService{

    @Autowired
    RaceRepository raceRepository;

    @Override
    public ResponseEntity<List<Race>> findAll() {
        try {
            return new ResponseEntity<>(raceRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        log.info("Inside update {}", requestMap);
        try {
            Optional<Race> optRace = raceRepository.findById(Integer.parseInt(requestMap.get("id")));
            if (optRace.isPresent()) {
                raceRepository.save(updateRaceFromMap(optRace.get(), requestMap));
                return ChebetUtils.getResponseEntity("Atualizado com sucesso!", HttpStatus.OK);
            } else {
                return ChebetUtils.getResponseEntity("Corrida não encontrada.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Race updateRaceFromMap(Race race, Map<String, String> requestMap) throws ParseException {
        if(requestMap.containsKey("pilot1Time") && requestMap.get("pilot1Time") != null) race.setPilot1Time(ChebetUtils.stringToLocalTime(requestMap.get("pilot1Time")));
        if(requestMap.containsKey("pilot1Broke")) race.setPilot1Broke(Boolean.parseBoolean(requestMap.get("pilot1Broke")));
        if(requestMap.containsKey("pilot2Time") && requestMap.get("pilot2Time") != null) race.setPilot2Time(ChebetUtils.stringToLocalTime(requestMap.get("pilot2Time")));
        if(requestMap.containsKey("pilot2Broke")) race.setPilot2Broke(Boolean.parseBoolean(requestMap.get("pilot2Broke")));
        return race;
    }

    @Override
    public ResponseEntity<List<Race>> findByChampionship(int championshipId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByChampionship'");
    }
    
}
