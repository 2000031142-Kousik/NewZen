package com.ems.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ems.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByDate(LocalDate date);

    Attendance findByDateAndStatuses(LocalDate date, Map<Integer, String> statuses);

    @Query("SELECT a FROM Attendance a " +
           "INNER JOIN a.attendanceDetails ad " +
           "WHERE ad.employeeId = :employeeId " +
           "AND a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findByDateRangeAndEmployeeId(@Param("employeeId") int employeeId,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);

    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
