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
@Table(name="business_lines")
public class BusinessLine {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String sector;
	private String commodity;
	private String description;
	@Column(name="start_date")
	private Date startDate;
	@Column(name="employee_count")
	private int employeeCount;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="business_entity_id")
	private BusinessEntity businessEntity;
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "businessLine")
	private Application application;
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "businessLine")
	private Patent patent;
	
	
	public BusinessLine() {
		super();
	}

	public BusinessLine(String sector, String commodity, String description, Date startDate, int employeeCount,
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

	public Patent getPatent() {
		return patent;
	}

	public void setPatent(Patent patent) {
		this.patent = patent;
	}
	
	
	

}
