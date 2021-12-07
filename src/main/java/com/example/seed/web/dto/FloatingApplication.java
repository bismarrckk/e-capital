package com.example.seed.web.dto;

public class FloatingApplication {
	private long id;
	private String applicationPhase;
	private String sector;
	private String commodity;
	
	public FloatingApplication() {
		super();
	}

	public FloatingApplication(long id, String applicationPhase, String sector, String commodity) {
		super();
		this.id = id;
		this.applicationPhase = applicationPhase;
		this.sector = sector;
		this.commodity = commodity;
	}

	@Override
	public String toString() {
		return "FloatingApplication [id=" + id + ", applicationPhase=" + applicationPhase + ", sector=" + sector
				+ ", commodity=" + commodity + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getApplicationPhase() {
		return applicationPhase;
	}

	public void setApplicationPhase(String applicationPhase) {
		this.applicationPhase = applicationPhase;
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
	
	
	
	
}
