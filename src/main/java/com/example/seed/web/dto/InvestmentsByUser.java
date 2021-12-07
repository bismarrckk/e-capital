package com.example.seed.web.dto;

import java.util.Date;

public class InvestmentsByUser {
	private long id;
	private Date applicationDate;
	private double targetAmount;
	public InvestmentsByUser() {
		super();
	}
	public InvestmentsByUser(long id,double targetAmount, Date applicationDate) {
		super();
		this.id = id;
		this.applicationDate = applicationDate;
		this.targetAmount = targetAmount;
	
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
	public double getTargetAmount() {
		return targetAmount;
	}
	public void setTargetAmount(double targetAmount) {
		this.targetAmount = targetAmount;
	}

	
	
	
}
