package com.deopraglabs.chebet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deopraglabs.chebet.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);

    public User findByCpf(String cpf);

    public User findByPhoneNumber(String phoneNumber);

    public List<User> findAll();

}
