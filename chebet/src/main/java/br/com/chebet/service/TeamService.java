package br.com.chebet.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import br.com.chebet.model.Team;

public interface TeamService {
    public ResponseEntity<List<Team>> findAll();

    public ResponseEntity<Team> findById(int id);

    public ResponseEntity<String> delete(int id);

    public ResponseEntity<String> update(Map<String, String> requestMap);

    public boolean isTeamRepositoryWorking();
}