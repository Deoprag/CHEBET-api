package br.com.chebet.serviceImpl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import br.com.chebet.model.Championship;
import br.com.chebet.model.Pilot;
import br.com.chebet.repository.ChampionshipRepository;
import br.com.chebet.repository.PilotRepository;
import br.com.chebet.service.ChampionshipService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChampionshipServiceImpl implements ChampionshipService{

    @Autowired
    ChampionshipRepository championshipRepository;
    
    @Autowired
    PilotRepository pilotRepository;

    @Override
    public ResponseEntity<String> register(Map<String, String> requestMap) {
        log.info("Inside register {}", requestMap);
        try {
            if (validateRegisterFields(requestMap)) {
                try {
                    Championship championship = championshipRepository.findByName(requestMap.get("name"));
                    if(Objects.isNull(championship)) {
                        championshipRepository.save(getChampionshipFromMap(requestMap));
                        return ChebetUtils.getResponseEntity("Registrado com sucesso!", HttpStatus.OK);
                    } else {
                        return ChebetUtils.getResponseEntity("O nome informado para o campeonato já está em uso!",
                            HttpStatus.BAD_REQUEST);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return ChebetUtils.getResponseEntity("Erro ao registrar campeonato!",
                HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.INVALID_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public boolean validateRegisterFields(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("date") && requestMap.containsKey("endDate") && requestMap.containsKey("pilots")) {
            return true;
        }
        return false;
    }

    public Championship getChampionshipFromMap(Map<String, String> requestMap) throws ParseException, JsonMappingException, JsonProcessingException {
        Championship championship = new Championship();
        championship.setName(requestMap.get("name"));
        championship.setDate(ChebetUtils.stringToLocalDateTime(requestMap.get("date")));
        championship.setEndDate(ChebetUtils.stringToLocalDateTime(requestMap.get("endDate")));
        championship.setPilots(getPilotList(requestMap.get("pilots")));
        return championship;
    }

    public List<Pilot> getPilotList(String pilotsIds) {
        try {
            List<Pilot> pilots = new ArrayList<>();
            String[] ids = pilotsIds.split(", ");
            for (String id : ids) {
                int pilotId = Integer.parseInt(id.trim());
                Optional<Pilot> pilot = pilotRepository.findById(pilotId);
                if (pilot.isPresent()) {
                    pilots.add(pilot.get());
                }
            }
            return pilots;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public ResponseEntity<List<Championship>> findAll() {
        try {
            return new ResponseEntity<>(championshipRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Championship> findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public ResponseEntity<String> delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
