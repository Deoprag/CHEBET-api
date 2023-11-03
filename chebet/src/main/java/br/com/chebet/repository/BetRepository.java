package br.com.chebet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Bet;
import br.com.chebet.model.Championship;

public interface BetRepository extends JpaRepository<Bet, Integer>{

    List<Bet> findAllByChampionship(Championship championship);
}
