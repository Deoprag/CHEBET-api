package br.com.chebet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Pilot;
import br.com.chebet.model.Team;

public interface PilotRepository extends JpaRepository<Pilot, Integer>{
    public List<Pilot> findByTeam(Team team);

    public Pilot findByName(String name);

    public Pilot findByNickname(String nickname);
}
