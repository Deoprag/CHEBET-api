package br.com.chebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    
}
