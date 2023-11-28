package br.com.chebet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Transaction;
import br.com.chebet.model.User;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    public List<Transaction> findAllByUser(User user);
}
