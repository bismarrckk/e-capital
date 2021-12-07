package com.example.seed.web.dto;


import javax.validation.constraints.Size;

import com.example.seed.entity.BusinessLine;

public class PatentDto {
	private long id;
	@Size(min=1,message="Title cannot be null")
	private String title;
	@Size(min=1,message="Licensing Institution cannot be null")
	private String licensingInstitution;
	@Size(min=1,message="File cannot be null")
	private String image;
	@Size(min=1,message="Description cannot be null")
	private String description;
	private long licenseNumber;
	private BusinessLine businessLine;
	public PatentDto() {
		super();
	}
	

	public PatentDto(long id, @Size(min = 1, message = "Title cannot be null") String title,
			@Size(min = 1, message = "Licensing Institution cannot be null") String licensingInstitution,
			@Size(min = 1, message = "File cannot be null") String image,
			@Size(min = 1, message = "Description cannot be null") String description,
			long licenseNumber,
			BusinessLine businessLine) {
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
	public String getlicensingInstitution() {
		return licensingInstitution;
	}
	public void setlicensingInstitution(String licensingInstitution) {
		this.licensingInstitution = licensingInstitution;
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
	
	
}
