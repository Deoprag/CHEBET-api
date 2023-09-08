package br.com.chebet.serviceImpl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Car;
import br.com.chebet.model.Color;
import br.com.chebet.model.Pilot;
import br.com.chebet.model.Preparer;
import br.com.chebet.repository.CarRepository;
import br.com.chebet.repository.PilotRepository;
import br.com.chebet.repository.PreparerRepository;
import br.com.chebet.service.CarService;

@Service
public class CarServiceImpl implements CarService{

    @Autowired
    CarRepository carRepository;

    @Autowired
    PilotRepository pilotRepository;

    @Autowired
    PreparerRepository preparerRepository;

    @Override
    public ResponseEntity<List<Car>> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ResponseEntity<Car> findById(int id) {
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

    @Override
    public boolean isCarRepositoryWorking() {
        try {    
            Car car = new Car();
            car.setNickname("Carrão massa");
            car.setYear(Short.parseShort("1991"));
            car.setModel("Chevete");
            car.setColor(Color.White);
            Optional<Pilot> obj = pilotRepository.findById(1);
            if (!Objects.isNull(obj)) {
                car.setPilot(obj.get());
            }
            Optional<Preparer> obj1 = preparerRepository.findById(1);
            if (!Objects.isNull(obj1)) {
                car.setPreparer(obj1.get());
            }
            System.out.println("Sets OK");
            carRepository.save(car);
            System.out.println("Salvo OK");
            car.setNickname("Carrão Massa");
            carRepository.save(car);
            System.out.println("Atualizado OK");
            carRepository.delete(car);
            System.out.println("Apagado OK");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;    
    }
}
