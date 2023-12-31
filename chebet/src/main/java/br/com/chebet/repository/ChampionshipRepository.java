package br.com.chebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.chebet.model.Championship;
import jakarta.transaction.Transactional;

public interface ChampionshipRepository extends JpaRepository<Championship, Integer>{
    
    public Championship findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "CALL GenerateRaceData(:id)", nativeQuery = true)
    void generateRaceData(@Param("id") int id);
}
