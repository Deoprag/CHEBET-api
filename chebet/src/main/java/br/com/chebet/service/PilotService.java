package br.com.chebet.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import br.com.chebet.model.Pilot;

public interface PilotService {
    public ResponseEntity<String> register(Map<String, String> requestMap);

    public ResponseEntity<List<Pilot>> findAll();

    public ResponseEntity<Pilot> findById(int id);

    public ResponseEntity<String> delete(int id);

    public ResponseEntity<String> update(Map<String, String> requestMap);
}
