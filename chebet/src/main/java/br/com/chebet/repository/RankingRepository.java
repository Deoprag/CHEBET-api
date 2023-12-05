package br.com.chebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.chebet.model.Pilot;
import br.com.chebet.model.Ranking;
import jakarta.transaction.Transactional;

public interface RankingRepository extends JpaRepository<Ranking, Integer>{

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateRanking(:id)", nativeQuery = true)
    void generateRanking(@Param("id") int id);
    
    @Query(value = "SELECT pilot_id FROM TB_Ranking WHERE championship_id = (:id) AND position = 1", nativeQuery = true)
    int getWinner(@Param("id") int id);
}
