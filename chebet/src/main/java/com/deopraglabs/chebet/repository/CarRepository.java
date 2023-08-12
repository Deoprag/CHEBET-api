package com.deopraglabs.chebet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deopraglabs.chebet.model.Car;

public interface CarRepository extends JpaRepository<Car, Integer>{
    
}
