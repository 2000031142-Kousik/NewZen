package com.ems.repository;

import com.ems.entity.SellerExpenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerExpenditureRepository extends JpaRepository<SellerExpenditure, Integer> {
}
