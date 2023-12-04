package br.com.chebet.repository;

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
}
