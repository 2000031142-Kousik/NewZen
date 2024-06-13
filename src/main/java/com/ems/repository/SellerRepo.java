package com.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.entity.Seller;

@Repository
public interface SellerRepo extends JpaRepository<Seller, Integer> {

}
