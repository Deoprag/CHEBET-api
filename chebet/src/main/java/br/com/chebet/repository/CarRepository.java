package br.com.chebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Car;


public interface CarRepository extends JpaRepository<Car, Integer>{
    public Car findByPilotId(int pilotId);

    public Car findByNickname(String nickname);
}
