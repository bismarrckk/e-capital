package com.example.seed.web.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.example.seed.entity.ApplicationPhase;
import com.example.seed.entity.ApplicationType;
import com.example.seed.entity.BusinessLine;

public class ApplicationDto {
	private long id;
	private Date applicationDate;
	private ApplicationType type;
	private ApplicationPhase applicationPhase;
	private BusinessLine businessLine;
	@Min(value=100, message="Target amount cannot be less than 100")
	private double targetAmount;
	@Size(min=3,message="Funding description cannot be empty")
	private String description;
	
	public ApplicationDto() {
		super();
	}
	
	public ApplicationDto(Date applicationDate, ApplicationType type, ApplicationPhase applicationPhase,
			double targetAmount, String description) {
		super();
		this.applicationDate = applicationDate;
		this.type = type;
		this.applicationPhase = applicationPhase;
		this.targetAmount = targetAmount;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public ApplicationType getType() {
		return type;
	}

	public void setType(ApplicationType type) {
		this.type = type;
	}

	public ApplicationPhase getApplicationPhase() {
		return applicationPhase;
	}

	public void setApplicationPhase(ApplicationPhase applicationPhase) {
		this.applicationPhase = applicationPhase;
	}

	public BusinessLine getBusinessLine() {
		return businessLine;
	}

	public void setBusinessLine(BusinessLine businessLine) {
		this.businessLine = businessLine;
	}

	public double getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(double targetAmount) {
		this.targetAmount = targetAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	
}
