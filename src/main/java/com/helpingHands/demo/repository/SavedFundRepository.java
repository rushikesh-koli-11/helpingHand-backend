package com.helpingHands.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpingHands.demo.entities.SavedFund;

public interface SavedFundRepository extends JpaRepository<SavedFund, Integer>{

}
