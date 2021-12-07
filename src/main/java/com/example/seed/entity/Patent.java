package com.example.seed.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="patents")
public class Patent {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String title;
	@Column(name="licensing_institution")
	private String licensingInstitution;
	private String image;
	private String description;
	@Column(name="license_number")
	private long licenseNumber;
	@OneToOne
    @JoinColumn(name = "business_line_id", referencedColumnName = "id")
	private BusinessLine businessLine;
	public Patent() {
		super();
	}
	
	public Patent(long id, String title, String licensingInstitution, String image, String description,
			long licenseNumber, BusinessLine businessLine) {
		super();
		this.id = id;
		this.title = title;
		this.licensingInstitution = licensingInstitution;
		this.image = image;
		this.description = description;
		this.licenseNumber = licenseNumber;
		this.businessLine = businessLine;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BusinessLine getBusinessLine() {
		return businessLine;
	}
	public void setBusinessLine(BusinessLine businessLine) {
		this.businessLine = businessLine;
	}

	public long getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(long licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getLicensingInstitution() {
		return licensingInstitution;
	}

	public void setLicensingInstitution(String licensingInstitution) {
		this.licensingInstitution = licensingInstitution;
	}
	
	
}
