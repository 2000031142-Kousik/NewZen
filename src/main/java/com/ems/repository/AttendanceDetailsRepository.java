// AttendanceDetailsRepository.java
package com.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ems.entity.AttendanceDetails;

public interface AttendanceDetailsRepository extends JpaRepository<AttendanceDetails, Integer> {
}
