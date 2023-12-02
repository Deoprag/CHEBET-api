package br.com.chebet.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Transaction;
import br.com.chebet.model.User;

@Service
public interface TransactionService {
    public ResponseEntity<String> register(Map<String, String> requestMap);

    public ResponseEntity<List<Transaction>> findAll();

    public ResponseEntity<Transaction> findById(int id);

    public ResponseEntity<String> delete(int id);

    public ResponseEntity<String> update(Map<String, String> requestMap);

    public ResponseEntity<List<Transaction>> findAllByUser(int id);
    
}
