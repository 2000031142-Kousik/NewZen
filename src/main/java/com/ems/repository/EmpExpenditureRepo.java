package com.ems.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ems.entity.EmpExpenditure;

public interface EmpExpenditureRepo extends JpaRepository<EmpExpenditure, Integer> {
    @Query("SELECT e FROM EmpExpenditure e WHERE e.employee.id = :employeeId AND e.expenditureDate BETWEEN :startDate AND :endDate")
    List<EmpExpenditure> findByEmployeeAndDateRange(@Param("employeeId") int employeeId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
