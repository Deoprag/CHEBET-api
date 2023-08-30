package br.com.chebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Bet;

public interface BetRepository extends JpaRepository<Bet, Integer>{
    
}
