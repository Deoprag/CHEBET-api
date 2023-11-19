package br.com.chebet.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Championship;

@Service
public interface ChampionshipService {
    public ResponseEntity<List<Championship>> findAll();

    public ResponseEntity<Championship> findById(int id);

    public ResponseEntity<String> delete(int id);

    public ResponseEntity<String> update(Map<String, String> requestMap);

}
