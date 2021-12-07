package com.example.seed.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="applications")
public class Application {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(name="application_date")
	private Date applicationDate;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="application_type_id")
	private ApplicationType type;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="application_phase_id")
	private ApplicationPhase applicationPhase;
	@OneToOne
    @JoinColumn(name = "business_line_id", referencedColumnName = "id")
	private BusinessLine businessLine;
	private double targetAmount;
	private String description;
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "application")
	private Investment investment;
	
	
	public Application() {
		super();
	}


	public Application(Date applicationDate, ApplicationType type, ApplicationPhase applicationPhase,
			BusinessLine businessLine, double targetAmount, String description) {
		super();
		this.applicationDate = applicationDate;
		this.type = type;
		this.applicationPhase = applicationPhase;
		this.businessLine = businessLine;
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


	public Investment getInvestment() {
		return investment;
	}


	public void setInvestment(Investment investment) {
		this.investment = investment;
	}
	
	
	
}
