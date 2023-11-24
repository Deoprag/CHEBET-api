package br.com.chebet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.chebet.model.Car;
import br.com.chebet.model.Preparer;
import br.com.chebet.model.Team;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer>{
    public Car findByPilotId(int pilotId);

    public Car findByNickname(String nickname);

    public List<Car> findCarsByPreparer(Preparer preparer);
}
