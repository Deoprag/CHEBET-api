package br.com.chebet.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import br.com.chebet.model.Bet;

public interface BetService {
    public ResponseEntity<List<Bet>> findAll();

    public ResponseEntity<Bet> findById(int id);

    public ResponseEntity<String> delete(int id);

    public ResponseEntity<String> update(Map<String, String> requestMap);

    public boolean isBetRepositoryWorking();
}