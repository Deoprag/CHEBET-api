package com.deopraglabs.chebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deopraglabs.chebet.model.Pilot;

public interface PilotRepository extends JpaRepository<Pilot, Integer>{
    
}
