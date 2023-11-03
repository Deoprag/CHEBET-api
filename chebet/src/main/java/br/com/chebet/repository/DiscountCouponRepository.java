package br.com.chebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chebet.model.Preparer;

public interface DiscountCouponRepository extends JpaRepository<Preparer, Integer>{
    
}
