package br.com.chebet.serviceImpl;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Gender;
import br.com.chebet.model.Pilot;
import br.com.chebet.model.Role;
import br.com.chebet.model.Team;
import br.com.chebet.model.Pilot;
import br.com.chebet.repository.CarRepository;
import br.com.chebet.repository.PilotRepository;
import br.com.chebet.repository.TeamRepository;
import br.com.chebet.service.PilotService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PilotServiceImpl implements PilotService {
    
    @Autowired
    PilotRepository pilotRepository;
    
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    CarRepository carRepository;

    @Override
    public ResponseEntity<String> register(Map<String, String> requestMap) {
        log.info("Inside register {}", requestMap);
        try {
            if (validateRegisterFields(requestMap)) {
                try {
                    Pilot pilot = pilotRepository.findByNickname(requestMap.get("nickname"));
                    if (Objects.isNull(pilot)) {
                        if (ChebetUtils.isOverage(ChebetUtils.stringToLocalDate(requestMap.get("birthDate")))) {
                            pilotRepository.save(getPilotFromMap(requestMap));
                            return ChebetUtils.getResponseEntity("Registrado com sucesso!", HttpStatus.OK);
                        } else {
                            return ChebetUtils.getResponseEntity("Data de nascimento inválida. Por favor, certifique-se de que o piloto é maior de idade!",
                            HttpStatus.BAD_REQUEST);   
                        }
                    } else {
                        return ChebetUtils.getResponseEntity("O apelido informado já está em uso!",
                                HttpStatus.BAD_REQUEST);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ChebetUtils.getResponseEntity("Erro ao registrar piloto!",
                    HttpStatus.BAD_REQUEST);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.INVALID_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Pilot>> findAll() {
        try {
            return new ResponseEntity<>(pilotRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Pilot>> findAllActives() {
        try {
            return new ResponseEntity<>(pilotRepository.findAllByActive(true), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Pilot>> findByTeam(int teamId) {
        try {
            Optional<Team> team = teamRepository.findById(teamId);
            List<Pilot> list = new ArrayList<>();
            for (Pilot pilot : list) {
                if (pilot.isActive()) {
                    list.add(pilot);
                }
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Pilot> findById(int id) {
        try {
            Optional<Pilot> pilot = pilotRepository.findById(id);
            if (pilot.isPresent()) {
                return new ResponseEntity<Pilot>(pilot.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<Pilot>(new Pilot(), HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<Pilot>(new Pilot(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Pilot>(new Pilot(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> delete(int id) {
        log.info("Inside delete {}", id);
        try {
            Optional<Pilot> optPilot = pilotRepository.findById(id);
            if (optPilot.isPresent()) {
                pilotRepository.delete(optPilot.get());
                return ChebetUtils.getResponseEntity("Apagado com sucesso!", HttpStatus.OK);
            } else {
                return ChebetUtils.getResponseEntity("Piloto não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            return ChebetUtils.getResponseEntity("Não é possível excluir este piloto, pois ele está associado a outros dados no sistema.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        log.info("Inside update {}", requestMap);
        try {
            Optional<Pilot> optPilot = pilotRepository.findById(Integer.parseInt(requestMap.get("id")));
            if (optPilot.isPresent()) {
                pilotRepository.save(updatePilotFromMap(optPilot.get(), requestMap));
                return ChebetUtils.getResponseEntity("Atualizado com sucesso!", HttpStatus.OK);
            } else {
                return ChebetUtils.getResponseEntity("Piloto não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Pilot updatePilotFromMap(Pilot pilot, Map<String, String> requestMap) throws ParseException {
        if (requestMap.containsKey("name")) pilot.setName(requestMap.get("name"));
        if (requestMap.containsKey("nickname")) pilot.setNickname(requestMap.get("nickname"));
        if (requestMap.containsKey("birthDate")) pilot.setBirthDate(ChebetUtils.stringToLocalDate(requestMap.get("birthDate")));
        if (requestMap.containsKey("team")) {
            try {
                Team team = teamRepository.findByName(requestMap.get("team"));
                pilot.setTeam(team);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestMap.containsKey("active")) pilot.setActive(Boolean.parseBoolean(requestMap.get("active")));
        return pilot;
    }

    public boolean validateRegisterFields(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("nickname") && requestMap.containsKey("birthDate") && requestMap.containsKey("team")
                && requestMap.containsKey("active")) {
            return true;
        }
        return false;
    }

    public Pilot getPilotFromMap(Map<String, String> requestMap) throws ParseException {
        Pilot pilot = new Pilot();
        pilot.setName(requestMap.get("name"));
        if (requestMap.containsKey("nickname")) pilot.setNickname(requestMap.get("nickname"));
        pilot.setBirthDate(ChebetUtils.stringToLocalDate(requestMap.get("birthDate")));
        try {
            Team team = teamRepository.findByName(requestMap.get("team"));
            pilot.setTeam(team);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pilot.setActive(Boolean.parseBoolean(requestMap.get("active")));
        return pilot;
    }

}
