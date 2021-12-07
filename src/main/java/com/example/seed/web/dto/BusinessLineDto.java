package com.example.seed.web.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.seed.entity.Application;
import com.example.seed.entity.BusinessEntity;

public class BusinessLineDto {
	private long id;
	@Size(min=2,message="Business sector cannot be empty")
	private String sector;
	@Size(min=2,message="Commodity cannot be empty")
	private String commodity;
	@Size(min=2,message="Business description cannot be empty")
	private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	@Min(value=1,message="employee count cannot be less than 1")
	private int employeeCount;
	private BusinessEntity businessEntity;
	private Application application;
	
	public BusinessLineDto() {
		super();
	}

	public BusinessLineDto(String sector, String commodity, String description, Date startDate, int employeeCount,
			BusinessEntity businessEntity,Application application) {
		super();
		this.sector = sector;
		this.commodity = commodity;
		this.description = description;
		this.startDate = startDate;
		this.employeeCount = employeeCount;
		this.businessEntity = businessEntity;
		this.application=application;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public BusinessEntity getBusinessEntity() {
		return businessEntity;
	}

	public void setBusinessEntity(BusinessEntity businessEntity) {
		this.businessEntity = businessEntity;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
	
	
}
