package com.ems.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sellerdetails")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private String phoneno;
    private String govtidtype;
    private String govtidno;

    public Seller() {
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
		return "Seller [id=" + id + ", name=" + name + ", address=" + address + ", phoneno=" + phoneno + ", govtidtype="
				+ govtidtype + ", govtidno=" + govtidno + "]";
	}
}