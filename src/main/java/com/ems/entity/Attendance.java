package com.ems.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    @ElementCollection
    @CollectionTable(name = "attendance_status", joinColumns = @JoinColumn(name = "attendance_id"))
    @MapKeyColumn(name = "employee_id")
    @Column(name = "status")
    private Map<Integer, String> statuses;

    @OneToMany(mappedBy = "attendance", cascade = CascadeType.ALL)
    private List<AttendanceDetails> attendanceDetails;

    // Constructors, getters, and setters
    public Attendance() {
    }

    public Attendance(LocalDate date, Map<Integer, String> statuses, List<AttendanceDetails> attendanceDetails) {
        this.date = date;
        this.statuses = statuses;
        this.attendanceDetails = attendanceDetails;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<Integer, String> getStatuses() {
        return statuses;
    }

    public void setStatuses(Map<Integer, String> statuses) {
        this.statuses = statuses;
    }

    public List<AttendanceDetails> getAttendanceDetails() {
        return attendanceDetails;
    }

    public void setAttendanceDetails(List<AttendanceDetails> attendanceDetails) {
        this.attendanceDetails = attendanceDetails;
    }
}
