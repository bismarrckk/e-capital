package com.example.seed.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="business_entities")
public class BusinessEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String email;
	private String location;
	@ManyToOne(fetch=FetchType.EAGER) 
	@JoinColumn(name="user_id")
	private User contactPerson;
	@Column(name="registration_number")
	private long registrationNumber;
	@Column(name="legal_name")
	private String legalName;
	@Column(name="kra_pin")
	private String kraPin;
	@OneToMany(mappedBy="businessEntity",cascade= {CascadeType.ALL})
	private List<BusinessLine> businesslines;
	
	public BusinessEntity() {
		super();
	}

	

	public BusinessEntity(String email, String location, User contactPerson, long registrationNumber, String legalName,
			String kraPin) {
		super();
		this.email = email;
		this.location = location;
		this.contactPerson = contactPerson;
		this.registrationNumber = registrationNumber;
		this.legalName = legalName;
		this.kraPin = kraPin;
	
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public User getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(User contactPerson) {
		this.contactPerson = contactPerson;
	}



	public long getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(long registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getKraPin() {
		return kraPin;
	}

	public void setKraPin(String kraPin) {
		this.kraPin = kraPin;
	}

	public List<BusinessLine> getBusinesslines() {
		return businesslines;
	}

	public void setBusinesslines(List<BusinessLine> businesslines) {
		this.businesslines = businesslines;
	}
	
	
	
	
}
