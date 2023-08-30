package br.com.chebet.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import br.com.chebet.model.Transaction;

public interface TransactionService {
    public ResponseEntity<List<Transaction>> findAll();

    public ResponseEntity<Transaction> findById(int id);

    public ResponseEntity<String> delete(int id);

    public ResponseEntity<String> update(Map<String, String> requestMap);

    public boolean isTransactionRepositoryWorking();
}
