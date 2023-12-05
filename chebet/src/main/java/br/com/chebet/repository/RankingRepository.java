package br.com.chebet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.chebet.model.Ranking;
import jakarta.transaction.Transactional;

public interface RankingRepository extends JpaRepository<Ranking, Integer>{

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateRanking(:id)", nativeQuery = true)
    void generateRanking(@Param("id") int id);
    
    @Query(value = "SELECT pilot_id FROM TB_Ranking WHERE championship_id = (:id) AND position = 1", nativeQuery = true)
    int getWinner(@Param("id") int id);
    
    @Query(value = "SELECT r.position FROM TB_Ranking r WHERE championship_id = (:id) AND pilot_id = (:pilot_id)", nativeQuery = true)
    short getPosition(@Param("id") int id, @Param("pilot_id") int pilot_id);

    @Query(value = "SELECT SEC_TO_TIME(AVG(TIME_TO_SEC(total_time))) " +
                  "FROM (" +
                  "  SELECT TIME_TO_SEC(TIMEDIFF(pilot1_time, '00:00:00')) AS total_time " +
                  "  FROM tb_race " +
                  "  WHERE pilot1_broke = false AND championship_id = (:championshipId) " +
                  "  UNION ALL " +
                  "  SELECT TIME_TO_SEC(TIMEDIFF(pilot2_time, '00:00:00')) AS total_time " +
                  "  FROM tb_race " +
                  "  WHERE pilot2_broke = false AND championship_id = (:championshipId)" +
                  ") AS subquery", nativeQuery = true)
   String getAverageTime(@Param("championshipId") int championshipId);
}
