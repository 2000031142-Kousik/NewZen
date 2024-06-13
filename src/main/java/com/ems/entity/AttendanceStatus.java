package com.ems.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AttendanceStatus {
    @Column(name = "status")
    private String status;

    @Column(name = "in_time")
    private LocalTime inTime;

    @Column(name = "out_time")
    private LocalTime outTime;

    public AttendanceStatus() {
    }

    public AttendanceStatus(String status, LocalTime inTime, LocalTime outTime) {
        this.status = status;
        this.inTime = inTime;
        this.outTime = outTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalTime getInTime() {
        return inTime;
    }

    public void setInTime(LocalTime inTime) {
        this.inTime = inTime;
    }

    public LocalTime getOutTime() {
        return outTime;
    }

    public void setOutTime(LocalTime outTime) {
        this.outTime = outTime;
    }
}