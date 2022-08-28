package jpa.dto;

import jpa.model.ExaminationReport;

public class ExaminationReportDTO {
	
	private Long id;
	private String textReport;
	private DiagnosisDTO diagnosis;
	private PrescriptionDTO prescription;
	private PatientDTO patient;
	
	public ExaminationReportDTO() {
		super();
	}
	
	public ExaminationReportDTO(ExaminationReport er) {
		super();
		id = er.getId();
		
		if(er.getDiagnosis() != null) {
			this.diagnosis = new DiagnosisDTO(er.getDiagnosis());
		} else {
			this.diagnosis = new DiagnosisDTO();
		}
		
		if (er.getPrescription() != null) {
			this.prescription = new PrescriptionDTO(er.getPrescription());
		} else {
			this.prescription = new PrescriptionDTO();
		}
		
		if (er.getPatient() != null) {
			this.patient = new PatientDTO(er.getPatient());
		} else {
			this.patient = new PatientDTO();
		}
		textReport = er.getTextReport();
	}
	
	public ExaminationReportDTO(String textReport) {
		super();
		this.diagnosis = new DiagnosisDTO();
		this.prescription = new PrescriptionDTO();
		this.textReport = textReport;
	}

	public String getTextReport() {
		return textReport;
	}

	public void setTextReport(String textReport) {
		this.textReport = textReport;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DiagnosisDTO getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(DiagnosisDTO diagnosis) {
		this.diagnosis = diagnosis;
	}

	public PrescriptionDTO getPrescription() {
		return prescription;
	}

	public void setPrescription(PrescriptionDTO prescription) {
		this.prescription = prescription;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}
	
	
}
