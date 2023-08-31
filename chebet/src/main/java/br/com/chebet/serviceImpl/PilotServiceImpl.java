package br.com.chebet.serviceImpl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Car;
import br.com.chebet.model.Pilot;
import br.com.chebet.model.Team;
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
                Optional<Team> team = teamRepository.findById(Integer.parseInt(requestMap.get("team")));
                if (team.isPresent()) {
                    Optional<Car> car = carRepository.findById(Integer.parseInt(requestMap.get("car")));
                    if (car.isPresent()) {
                        pilotRepository.save(getPilotFromMap(requestMap));
                        return ChebetUtils.getResponseEntity("Successfully registered!", HttpStatus.OK);
                    } else {
                        return ChebetUtils.getResponseEntity("Car doesn't exists.",
                            HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return ChebetUtils.getResponseEntity("Team doesn't exists.",
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }



    @Override
    public ResponseEntity<Pilot> findById(int id) {
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

    public boolean validateRegisterFields(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("birthDate") && requestMap.containsKey("team")
                && requestMap.containsKey("car")) {
            return true;
        }
        return false;
    }

    public Pilot getPilotFromMap(Map<String, String> requestMap) throws ParseException {
        Pilot pilot = new Pilot();
        pilot.setId(Integer.parseInt(requestMap.get("id")));
        pilot.setName(requestMap.get("name"));
        if (requestMap.containsKey("nickname")) {
            pilot.setNickname(requestMap.get("nickname"));
        }
        pilot.setBirthDate(ChebetUtils.stringToLocalDate("birthDate"));
        Optional<Team> team = teamRepository.findById(Integer.parseInt(requestMap.get("team")));
        if (team.isPresent()) {
            pilot.setTeam(team.get());
        }
        return pilot;
    }

}
