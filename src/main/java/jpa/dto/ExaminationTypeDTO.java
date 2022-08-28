package jpa.dto;

import jpa.model.ExaminationType;

public class ExaminationTypeDTO {

	private Long id;
	private String typeName;
	
	public ExaminationTypeDTO() {}
	
	public ExaminationTypeDTO(ExaminationType examinationType) {
		this(examinationType.getId(), examinationType.getTypeName());
	}
	
	public ExaminationTypeDTO(Long id, String typeName) {
		super();
		this.id = id;
		this.typeName = typeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
}
