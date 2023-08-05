package com.deopraglabs.chebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deopraglabs.chebet.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByCpf(String cpf);

    User findByPhoneNumber(String phoneNumber);
    
}
