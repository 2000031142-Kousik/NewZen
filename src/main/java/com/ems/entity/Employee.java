package com.ems.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="empdetails")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // this is id representing empid
    private String name;
    private String address;
    private String phoneno;
    private int slab; // Changed from String to int
    private double salary;
    private String govtidtype;
    private String govtidno;

    public Employee() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public int getSlab() { // Changed return type from String to int
        return slab;
    }

    public void setSlab(int slab) { // Changed parameter type from String to int
        this.slab = slab;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getGovtidtype() {
        return govtidtype;
    }

    public void setGovtidtype(String govtidtype) {
        this.govtidtype = govtidtype;
    }

    public String getGovtidno() {
        return govtidno;
    }

    public void setGovtidno(String govtidno) {
        this.govtidno = govtidno;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", address=" + address + ", phoneno=" + phoneno + ", slab="
                + slab + ", salary=" + salary + ", govtidtype=" + govtidtype + ", govtidno=" + govtidno + "]";
    }
}
