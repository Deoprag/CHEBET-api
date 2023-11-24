package br.com.chebet.serviceImpl;

import java.text.ParseException;
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

import br.com.chebet.model.Car;
import br.com.chebet.model.Preparer;
import br.com.chebet.model.Team;
import br.com.chebet.repository.CarRepository;
import br.com.chebet.repository.PreparerRepository;
import br.com.chebet.repository.TeamRepository;
import br.com.chebet.service.PreparerService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PreparerServiceImpl implements PreparerService {
  
    @Autowired
    PreparerRepository preparerRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    CarRepository carRepository;
  
    @Override
    public ResponseEntity<List<Preparer>> findAll() {
        try {
            return new ResponseEntity<>(preparerRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Preparer>> findByTeam(int teamId) {
        try {
            Optional<Team> team = teamRepository.findById(teamId);
            return new ResponseEntity<>(preparerRepository.findByTeam(team.get()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> register(Map<String, String> requestMap) {
        log.info("Inside register {}", requestMap);
        try {
            if (validateRegisterFields(requestMap)) {
                try {
                    Preparer preparer = preparerRepository.findByNickname(requestMap.get("nickname"));
                    if (Objects.isNull(preparer)) {
                        preparerRepository.save(getPreparerFromMap(requestMap));
                        return ChebetUtils.getResponseEntity("Registrado com sucesso!", HttpStatus.OK);
                    } else {
                        return ChebetUtils.getResponseEntity("O apelido informado já está em uso!",
                                HttpStatus.BAD_REQUEST);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ChebetUtils.getResponseEntity("Erro ao registrar preparador!",
                    HttpStatus.BAD_REQUEST);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.INVALID_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Preparer> findById(int id) {
        try {
            Optional<Preparer> preparer = preparerRepository.findById(id);
            if (preparer.isPresent()) {
                return new ResponseEntity<Preparer>(preparer.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<Preparer>(new Preparer(), HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<Preparer>(new Preparer(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Preparer>(new Preparer(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> delete(int id) {
        log.info("Inside delete {}", id);
        try {
            Optional<Preparer> optPreparer = preparerRepository.findById(id);
            if (optPreparer.isPresent()) {
                preparerRepository.delete(optPreparer.get());
                return ChebetUtils.getResponseEntity("Apagado com sucesso!", HttpStatus.OK);
            } else {
                return ChebetUtils.getResponseEntity("Preparador não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            return ChebetUtils.getResponseEntity("Não é possível excluir este preparador, pois ele está associado a outros dados no sistema.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        log.info("Inside update {}", requestMap);
        try {
            Optional<Preparer> optPreparer = preparerRepository.findById(Integer.parseInt(requestMap.get("id")));
            if (optPreparer.isPresent()) {
                if (optPreparer.get().getTeam().getId().equals(Integer.parseInt(requestMap.get("team")))) {
                    preparerRepository.save(updatePreparerFromMap(optPreparer.get(), requestMap));
                    return ChebetUtils.getResponseEntity("Atualizado com sucesso!", HttpStatus.OK);
                } else {
                    try {
                        List<Car> cars = carRepository.findCarsByPreparer(optPreparer.get());
                        for (Car car : cars) {
                            car.setPreparer(null);
                            carRepository.save(car);
                        }
                        preparerRepository.save(updatePreparerFromMap(optPreparer.get(), requestMap));
                        return ChebetUtils.getResponseEntity("Atualizado com sucesso!", HttpStatus.OK);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return ChebetUtils.getResponseEntity("Preparador não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Preparer updatePreparerFromMap(Preparer preparer, Map<String, String> requestMap) throws ParseException {
        if (requestMap.containsKey("name")) preparer.setName(requestMap.get("name"));
        if (requestMap.containsKey("nickname")) preparer.setNickname(requestMap.get("nickname"));
        if (requestMap.containsKey("team")) {
            try {
                Optional<Team> team = teamRepository.findById(Integer.parseInt(requestMap.get("team")));
                preparer.setTeam(team.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return preparer;
    }

    public boolean validateRegisterFields(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("nickname") && requestMap.containsKey("team")) {
            return true;
        }
        return false;
    }

    public Preparer getPreparerFromMap(Map<String, String> requestMap) throws ParseException {
        Preparer preparer = new Preparer();
        preparer.setName(requestMap.get("name"));
        preparer.setNickname(requestMap.get("nickname"));
        try {
            Optional<Team> team = teamRepository.findById(Integer.parseInt(requestMap.get("team")));
            preparer.setTeam(team.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparer;
    }

}
