package br.com.chebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    
}
