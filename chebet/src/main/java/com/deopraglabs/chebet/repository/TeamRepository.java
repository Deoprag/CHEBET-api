package com.deopraglabs.chebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deopraglabs.chebet.model.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    
}
