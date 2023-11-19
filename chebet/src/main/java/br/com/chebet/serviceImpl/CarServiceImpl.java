package br.com.chebet.serviceImpl;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import br.com.chebet.model.Color;
import br.com.chebet.model.Pilot;
import br.com.chebet.model.Preparer;
import br.com.chebet.model.Team;
import br.com.chebet.model.Car;
import br.com.chebet.repository.CarRepository;
import br.com.chebet.repository.PilotRepository;
import br.com.chebet.repository.PreparerRepository;
import br.com.chebet.service.CarService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CarServiceImpl implements CarService{

    @Autowired
    CarRepository carRepository;

    @Autowired
    PilotRepository pilotRepository;

    @Autowired
    PreparerRepository preparerRepository;

    @Override
    public ResponseEntity<String> register(Map<String, String> requestMap) {
        log.info("Inside register {}", requestMap);
        try {
            if (validateRegisterFields(requestMap)) {
                if (!(Integer.parseInt(requestMap.get("year")) < 1960) && !(Integer.parseInt(requestMap.get("year")) > LocalDate.now().getYear() + 1)) {
                    Car car = carRepository.findByNickname(requestMap.get("nickname"));
                    if (Objects.isNull(car)) {
                        car = carRepository.findByPilotId(Integer.parseInt(requestMap.get("pilot")));
                        if (Objects.isNull(car)) {
                            carRepository.save(getCarFromMap(requestMap));
                            return ChebetUtils.getResponseEntity("Registrado com sucesso!", HttpStatus.OK);
                        } else {
                            return ChebetUtils.getResponseEntity("Piloto já associado a outro carro!",
                            HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        return ChebetUtils.getResponseEntity("Já existe outro carro com esse apelido!",
                        HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return ChebetUtils.getResponseEntity("Ano inválido. O veículo não pode ter sido fabricado antes de 1960 nem após o ano atual.",
                    HttpStatus.BAD_REQUEST);
                }
            } else {
                return ChebetUtils.getResponseEntity("Erro ao registrar carro!",
                HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.INVALID_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        log.info("Inside update {}", requestMap);
        try {
            Optional<Car> optCar = carRepository.findById(Integer.parseInt(requestMap.get("id")));
            if (optCar.isPresent()) {
                if (!(Integer.parseInt(requestMap.get("year")) < 1960) && !(Integer.parseInt(requestMap.get("year")) > LocalDate.now().getYear() + 1)) {
                    Car car = carRepository.findByNickname(requestMap.get("nickname"));
                    if (Objects.isNull(car) || car.getId() == optCar.get().getId()) {
                        car = carRepository.findByPilotId(Integer.parseInt(requestMap.get("pilot")));
                        if (Objects.isNull(car)) {
                            carRepository.save(updateCarFromMap(optCar.get(), requestMap));
                            return ChebetUtils.getResponseEntity("Atualizado com sucesso!", HttpStatus.OK);
                        } else {
                            return ChebetUtils.getResponseEntity("Piloto já associado a outro carro!",
                            HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        return ChebetUtils.getResponseEntity("Já existe outro carro com esse apelido!",
                        HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return ChebetUtils.getResponseEntity("Ano inválido. O veículo não pode ter sido fabricado antes de 1960 nem após o ano atual.",
                    HttpStatus.BAD_REQUEST);
                }
            } else {
                return ChebetUtils.getResponseEntity("Carro não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Car getCarFromMap(Map<String, String> requestMap) throws ParseException {
        Car car = new Car();
        car.setNickname(requestMap.get("nickname"));
        car.setYear(Short.parseShort(requestMap.get("year")));
        car.setModel(requestMap.get("model"));
        car.setColor(Color.valueOf(requestMap.get("color")));
        Optional<Pilot> pilot = pilotRepository.findById(Integer.parseInt(requestMap.get("pilot")));
        Optional<Preparer> preparer = preparerRepository.findById(Integer.parseInt(requestMap.get("preparer")));
        try {
            car.setPilot(pilot.get());
            car.setPreparer(preparer.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return car;
    }

    public boolean validateRegisterFields(Map<String, String> requestMap) {
        if (requestMap.containsKey("nickname") && requestMap.containsKey("year") && requestMap.containsKey("model") && requestMap.containsKey("color") && requestMap.containsKey("pilot") && requestMap.containsKey("preparer")) {
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<List<Car>> findAll() {
        try {
            return new ResponseEntity<>(carRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Car> findById(int id) {
        try {
            Optional<Car> car = carRepository.findById(id);
            if (car.isPresent()) {
                return new ResponseEntity<Car>(car.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<Car>(new Car(), HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<Car>(new Car(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Car>(new Car(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> delete(int id) {
        log.info("Inside delete {}", id);
        try {
            Optional<Car> optCar = carRepository.findById(id);
            if (optCar.isPresent()) {
                carRepository.delete(optCar.get());
                return ChebetUtils.getResponseEntity("Apagado com sucesso!", HttpStatus.OK);
            } else {
                return ChebetUtils.getResponseEntity("Carro não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            return ChebetUtils.getResponseEntity("Não é possível excluir este veiculo, pois ele está associado a outros dados no sistema.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Car updateCarFromMap(Car car, Map<String, String> requestMap) throws ParseException {
        if (requestMap.containsKey("nickname")) car.setNickname(requestMap.get("nickname"));
        if (requestMap.containsKey("year")) car.setYear(Short.parseShort(requestMap.get("year")));
        if (requestMap.containsKey("model")) car.setModel(requestMap.get("model"));
        if (requestMap.containsKey("color")) car.setColor(Color.valueOf(requestMap.get("color")));
        try {
            if (requestMap.containsKey("pilot")) {
                Optional<Pilot> pilot = pilotRepository.findById(Integer.parseInt(requestMap.get("pilot")));
                car.setPilot(pilot.get());
            }
            if (requestMap.containsKey("preparer")) {
                Optional<Preparer> preparer = preparerRepository.findById(Integer.parseInt(requestMap.get("preparer")));
                car.setPreparer(preparer.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return car;
    }

}
