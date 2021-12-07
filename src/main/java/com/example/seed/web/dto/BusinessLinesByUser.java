package com.example.seed.web.dto;

import java.util.Date;

public class BusinessLinesByUser {
	private long id;
	private String sector;
	private String commodity;
	private Date startDate;
	private int employeeCount;
	private String legalName;
	public BusinessLinesByUser() {
		super();
	}
	public BusinessLinesByUser(long id, String sector, String commodity, Date startDate, int employeeCount,
			String legalName) {
		super();
		this.id = id;
		this.sector = sector;
		this.commodity = commodity;
		this.startDate = startDate;
		this.employeeCount = employeeCount;
		this.legalName = legalName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getEmployeeCount() {
		return employeeCount;
	}
	public void setEmployeeCount(int employeeCount) {
		this.employeeCount = employeeCount;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	
	
	
}
