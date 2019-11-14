package dto;

import modeli.Diagnosis;
import modeli.Patient;

public class DiagnosisDTO {
	private long id;
	private String name;
	
	public DiagnosisDTO() {
		
	}
	
	public DiagnosisDTO(long id2, String name2) {
		this.id=id;
		this.name=name;
		
	}
	
	public DiagnosisDTO(Diagnosis diagnosis) {
		this(diagnosis.getId(),diagnosis.getName());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
