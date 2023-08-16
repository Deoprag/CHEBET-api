package br.com.chebet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Car;

public interface CarRepository extends JpaRepository<Car, Integer>{
    
}
