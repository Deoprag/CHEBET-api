package br.com.chebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Championship;

public interface ChampionshipRepository extends JpaRepository<Championship, Integer>{
    
}
