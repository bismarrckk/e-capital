package com.example.seed.web.dto;

import java.util.Date;

public class ApplicationsByUser {
	private long id;
	private String commodity;
	private String sector;
	private String type;
	private String phase;
	private Date applicationDate;
	private double targetAmount;
	
	
	public ApplicationsByUser() {
		super();
	}


	public ApplicationsByUser(long id, double targetAmount,Date applicationDate,String commodity, String sector,  String phase,String type) {
		super();
		this.id = id;
		this.commodity = commodity;
		this.sector = sector;
		this.type = type;
		this.phase = phase;
		this.applicationDate = applicationDate;
		this.targetAmount=targetAmount;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getCommodity() {
		return commodity;
	}


	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}


	public String getSector() {
		return sector;
	}


	public void setSector(String sector) {
		this.sector = sector;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getPhase() {
		return phase;
	}


	public void setPhase(String phase) {
		this.phase = phase;
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
