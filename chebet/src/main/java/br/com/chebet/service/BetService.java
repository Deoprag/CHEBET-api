package br.com.chebet.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Bet;

@Service
public interface BetService {
    public ResponseEntity<List<Bet>> findAll();

    public ResponseEntity<List<Bet>> findAllByChampionship(int championshipId);
    
    public ResponseEntity<List<Bet>> findAllByUser(int userId);

    public ResponseEntity<Bet> findById(int id);

    public ResponseEntity<String> delete(int id);
    
    public ResponseEntity<String> register(Map<String, String> requestMap);

    public ResponseEntity<String> update(Map<String, String> requestMap);

}
