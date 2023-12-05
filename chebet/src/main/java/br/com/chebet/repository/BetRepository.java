package br.com.chebet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Bet;
import br.com.chebet.model.BetType;
import br.com.chebet.model.Championship;
import br.com.chebet.model.User;

public interface BetRepository extends JpaRepository<Bet, Integer>{

    List<Bet> findAllByChampionship(Championship championship);
    
    List<Bet> findAllByUser(User user);

    List<Bet> findAllByBetType(BetType betType);
}
