package br.com.chebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Pilot;

public interface PilotRepository extends JpaRepository<Pilot, Integer>{
    
}
