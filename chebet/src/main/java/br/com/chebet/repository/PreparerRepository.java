package br.com.chebet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Pilot;
import br.com.chebet.model.Preparer;
import br.com.chebet.model.Team;

public interface PreparerRepository extends JpaRepository<Preparer, Integer>{
    public List<Preparer> findByTeam(Team team);

    public Preparer findByNickname(String nickname);
}
