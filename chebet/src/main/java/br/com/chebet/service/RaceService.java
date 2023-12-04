package br.com.chebet.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Race;

@Service
public interface RaceService {
    public ResponseEntity<List<Race>> findAll();

    public ResponseEntity<String> update(Map<String, String> requestMap);

    public ResponseEntity<List<Race>> findByChampionship(int championshipId);
}
