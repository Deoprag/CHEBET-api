package br.com.chebet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Championship;
import br.com.chebet.model.Race;

public interface RaceRepository extends JpaRepository<Race, Integer>{

    public List<Race> findAllByChampionship(Championship championship);
}
