package com.ems.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.Attendance;
import com.ems.entity.AttendanceDetails;
import com.ems.repository.AttendanceRepository;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepo;

    public void markAttendance(Attendance attendance) {
        calculateTotalHoursWorked(attendance);
        attendanceRepo.save(attendance);
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        List<Attendance> attendances = attendanceRepo.findByDate(date);
        attendances.forEach(this::calculateTotalHoursWorked);
        return attendances;
    }

    public Attendance getAttendanceByDateAndStatuses(LocalDate date, Map<Integer, String> statuses) {
        Attendance attendance = attendanceRepo.findByDateAndStatuses(date, statuses);
        calculateTotalHoursWorked(attendance);
        return attendance;
    }

    public List<Attendance> getAllAttendances() {
        List<Attendance> attendances = attendanceRepo.findAll();
        attendances.forEach(this::calculateTotalHoursWorked);
        return attendances;
    }

    public List<Attendance> getAttendanceByDateRange(int employeeId, LocalDate startDate, LocalDate endDate) {
        List<Attendance> attendances = attendanceRepo.findByDateRangeAndEmployeeId(employeeId, startDate, endDate);
        attendances.forEach(this::calculateTotalHoursWorked);
        return attendances;
    }

    public List<Attendance> getAttendanceByMonthAndYear(int month, int year) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return attendanceRepo.findByDateBetween(startDate, endDate);
    }

    private void calculateTotalHoursWorked(Attendance attendance) {
        List<AttendanceDetails> attendanceDetails = attendance.getAttendanceDetails();
        for (AttendanceDetails detail : attendanceDetails) {
            detail.setTotalHoursWorked(detail.calculateTotalHours(detail.getIntime(), detail.getOuttime()));
        }
    }
}
