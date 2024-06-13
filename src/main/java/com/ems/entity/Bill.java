package com.ems.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="bills")
public class Bill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String date;
    private String name;
    private String place;
    private String vehicleNumber;
    private double fullVehicleWeight;
    private double emptyVehicleWeight;
    private double weight;
    private int bags;
    private double reductionWeight;
    private double finalWeight;
    private double rate;
    private double total;
    private String modeofpayment;
    private String dateOfPayment;
    private String invoiceNumber;
    
    @Column(length=64)
    private String photos;
    
    public Bill()
    {
    	
    }

    public Bill(String date, String name, String place, String vehicleNumber, double fullVehicleWeight,
			double emptyVehicleWeight, double weight, int bags, double reductionWeight, double finalWeight, double rate,
			double total, String modeofpayment, String dateOfPayment, String invoiceNumber, String photos) {
		super();
		this.date = date;
		this.name = name;
		this.place = place;
		this.vehicleNumber = vehicleNumber;
		this.fullVehicleWeight = fullVehicleWeight;
		this.emptyVehicleWeight = emptyVehicleWeight;
		this.weight = weight;
		this.bags = bags;
		this.reductionWeight = reductionWeight;
		this.finalWeight = finalWeight;
		this.rate = rate;
		this.total = total;
		this.modeofpayment = modeofpayment;
		this.dateOfPayment = dateOfPayment;
		this.invoiceNumber = invoiceNumber;
		this.photos = photos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public double getFullVehicleWeight() {
		return fullVehicleWeight;
	}

	public void setFullVehicleWeight(double fullVehicleWeight) {
		this.fullVehicleWeight = fullVehicleWeight;
	}

	public double getEmptyVehicleWeight() {
		return emptyVehicleWeight;
	}

	public void setEmptyVehicleWeight(double emptyVehicleWeight) {
		this.emptyVehicleWeight = emptyVehicleWeight;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getBags() {
		return bags;
	}

	public void setBags(int bags) {
		this.bags = bags;
	}

	public double getReductionWeight() {
		return reductionWeight;
	}

	public void setReductionWeight(double reductionWeight) {
		this.reductionWeight = reductionWeight;
	}

	public double getFinalWeight() {
		return finalWeight;
	}

	public void setFinalWeight(double finalWeight) {
		this.finalWeight = finalWeight;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getModeofpayment() {
		return modeofpayment;
	}

	public void setModeofpayment(String modeofpayment) {
		this.modeofpayment = modeofpayment;
	}

	public String getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(String dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", date=" + date + ", name=" + name + ", place=" + place + ", vehicleNumber="
				+ vehicleNumber + ", fullVehicleWeight=" + fullVehicleWeight + ", emptyVehicleWeight="
				+ emptyVehicleWeight + ", weight=" + weight + ", bags=" + bags + ", reductionWeight=" + reductionWeight
				+ ", finalWeight=" + finalWeight + ", rate=" + rate + ", total=" + total + ", modeofpayment="
				+ modeofpayment + ", dateOfPayment=" + dateOfPayment + ", invoiceNumber=" + invoiceNumber + ", photos="
				+ photos + "]";
	}

}