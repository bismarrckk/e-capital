package com.example.seed.web.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.example.seed.entity.BusinessLine;
import com.example.seed.entity.User;

public class BusinessEntityDto {
	private long id;
	@NotEmpty( message="Email Address cannot be empty")
	@Email( message="A valid email is required")
	private String email;
	@NotEmpty( message="Physical location cannot be empty")
	private String location;
	@Min(value=1000, message="A valid registration number is required")
	private long registrationNumber;
	@NotEmpty( message="Legal name cannot be empty")
	private String legalName;
	@NotEmpty( message="KRA pin cannot be empty")
	private String kraPin;
	private User contactPerson;
	private List<BusinessLine> businesslines;
	
	public BusinessEntityDto() {
		super();
	}

	public BusinessEntityDto(
			@NotEmpty(message = "Email Address cannot be empty") @Email(message = "A valid email is required") String email,
			@NotEmpty(message = "Physical location cannot be empty") String location,
			@Min(value = 1000, message = "A valid registration number is required") long registrationNumber,
			@NotEmpty(message = "Legal name cannot be empty") String legalName,
			@NotEmpty(message = "KRA pin cannot be empty") String kraPin,User contactPerson) {
		super();
		this.email = email;
		this.location = location;
		this.registrationNumber = registrationNumber;
		this.legalName = legalName;
		this.kraPin = kraPin;
		this.contactPerson=contactPerson;
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
	
	public User getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(User contactPerson) {
		this.contactPerson = contactPerson;
	}

	public List<BusinessLine> getBusinesslines() {
		return businesslines;
	}

	public void setBusinesslines(List<BusinessLine> businesslines) {
		this.businesslines = businesslines;
	}
	
	
	
}
