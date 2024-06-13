package com.ems.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "attendance_details")
public class AttendanceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "intime")
    private LocalTime intime;

    @Column(name = "outtime")
    private LocalTime outtime;

    @Column(name = "total_hours_worked")
    private double totalHoursWorked;

    @Column(name = "status") // Added status field
    private String status;

    @ManyToOne
    @JoinColumn(name = "attendance_id", nullable = false)
    private Attendance attendance;

    // Constructors, getters, and setters
    public AttendanceDetails() {
    }

    public AttendanceDetails(int employeeId, LocalTime intime, LocalTime outtime, Attendance attendance, String status) {
        this.employeeId = employeeId;
        this.intime = intime;
        this.outtime = outtime;
        this.attendance = attendance;
        this.status = status;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalTime getIntime() {
        return intime;
    }

    public void setIntime(LocalTime intime) {
        this.intime = intime;
    }

    public LocalTime getOuttime() {
        return outtime;
    }

    public void setOuttime(LocalTime outtime) {
        this.outtime = outtime;
    }

    public double getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public void setTotalHoursWorked(double totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double calculateTotalHours(LocalTime intime, LocalTime outtime) {
        return java.time.Duration.between(intime, outtime).toHours();
    }
}
