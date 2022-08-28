package jpa.dto;

import jpa.model.Clinic;

public class ClinicDTO {

	private Long id;
	private String name;
	private String adress;
	private String description;
	private double gradeSum;
	private int gradeNumber;
	private double longitude;
	private double latitude;
	
	public ClinicDTO() {
		
	}
	
	public ClinicDTO(Clinic clinic) {
		this.id = clinic.getId();
		this.name = clinic.getName();
		this.adress = clinic.getAdress();
		this.description = clinic.getDescription();
		this.gradeSum = clinic.getGradeSum();
		this.gradeNumber = clinic.getGradeNumber();
		this.longitude = clinic.getLongitude();
		this.latitude = clinic.getLatitude();
	}
	
	public ClinicDTO(Long id, String name, String adress, String description, double gradeSum, int gradeNumber, double longitude, double latitude) {
		super();
		this.id = id;
		this.name = name;
		this.adress = adress;
		this.description = description;
		this.gradeSum = gradeSum;
		this.gradeNumber = gradeNumber;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getAdress() {
		return adress;
	}
	public String getDescription() {
		return description;
	}
	public double getGradeSum() {
		return gradeSum;
	}
	public int getGradeNumber() {
		return gradeNumber;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	
	
}
