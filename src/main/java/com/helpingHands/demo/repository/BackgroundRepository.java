package com.helpingHands.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.Background;

@Repository
public interface BackgroundRepository extends JpaRepository<Background, Integer> {
}

