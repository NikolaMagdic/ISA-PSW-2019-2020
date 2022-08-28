package jpa.dto;

import jpa.model.Prescription;

public class PrescriptionDTO {
	private Long id;
	
	private String name;
	private boolean validated;
	private NurseDTO nurse;
	
	
	public PrescriptionDTO(Long id, String name, boolean validated) {
		super();
		this.id = id;
		this.name = name;
		this.validated = validated;
	}
	
	public PrescriptionDTO(String name, boolean validated) {
		super();
		this.name = name;
		this.validated = validated;
	}
	public PrescriptionDTO() {
		
	}
	public PrescriptionDTO(Prescription p) {
		this.id = p.getId();;
		this.name = p.getName();
		this.validated = p.isValidated();
		
		if(p.getNurse() != null) {
			this.nurse = new NurseDTO(p.getNurse());
		} else {
			this.nurse = new NurseDTO();
		}
		
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isValidated() {
		return validated;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public NurseDTO getNurse() {
		return nurse;
	}

	public void setNurse(NurseDTO nurse) {
		this.nurse = nurse;
	}

}
