package br.com.chebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Preparer;

public interface PreparerRepository extends JpaRepository<Preparer, Integer>{
    
}
