package com.ems.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sellerexpenditures")
public class SellerExpenditure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sellerId")
    private Seller seller;

    private LocalDate date;

    @Column(name = "expenditure_amount")
    private double expenditureAmount;

    public SellerExpenditure() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getExpenditureAmount() {
        return expenditureAmount;
    }

    public void setExpenditureAmount(double expenditureAmount) {
        this.expenditureAmount = expenditureAmount;
    }
}
