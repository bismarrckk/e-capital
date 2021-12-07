package com.example.seed.web.dto;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.seed.entity.BusinessEntity;
import com.example.seed.entity.Role;

public class UserDto {
	private long id;
	@Size(min=3,max=30,message="First Name cannot have less than three letters")
	private String firstName;
	@Size(min=3,max=30,message="Last Name cannot have less than three letters")
	private String lastName;
	@NotEmpty( message="Email Address cannot be empty")
	@Email( message="A valid email is required")
	private String email;
	@Min(value=100000000, message="A valid phone number is required")
	private long phoneNumber;
	private String idType;
	private long idNumber;
	@Size(min=5,message="Physical Address cannot have less than three letters")
	private String physicalAddress;
	@Size(min=8,message="Password should not have less than eight characters")
	private String password;
	private Collection<Role> roles;
	private List<BusinessEntity> businessEntities;
	private boolean enabled;
	private String gender;
	private String accountType;
	
	public UserDto() {
		super();
	}

	public UserDto(String firstName, String lastName, String email, long phoneNumber, String idType, long idNumber,
			String physicalAddress, String password,boolean enabled,String gender,String accountType) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.idType = idType;
		this.idNumber = idNumber;
		this.physicalAddress = physicalAddress;
		this.password = password;
		this.enabled=enabled;
		this.gender=gender;
		this.accountType=accountType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public long getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(long idNumber) {
		this.idNumber = idNumber;
	}

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public List<BusinessEntity> getBusinessEntities() {
		return businessEntities;
	}

	public void setBusinessEntities(List<BusinessEntity> businessEntities) {
		this.businessEntities = businessEntities;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	
	
	
}
