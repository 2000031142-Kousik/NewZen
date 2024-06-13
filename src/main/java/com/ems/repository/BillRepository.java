package com.ems.repository;

import org.springframework.data.repository.CrudRepository;

import com.ems.entity.Bill;

public interface BillRepository extends CrudRepository<Bill, Integer> {

}
