package jpa.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Prescription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	@Column(name = "validated")
	private boolean validated;
	
	// Report koji je povezan sa tim uputom
	@OneToMany(mappedBy = "prescription", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<ExaminationReport> examinationReports = new HashSet<ExaminationReport>();
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Nurse nurse;

	public Prescription(String naziv, boolean validated) {
		super();
		this.name = naziv;
		this.validated = validated;
	}
	public Prescription(Long id, String naziv, boolean validated) {
		super();
		this.id = id;
		this.name = naziv;
		this.validated = validated;
	}
	public Prescription() {
		
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
	public void setName(String naziv) {
		this.name = naziv;
	}
	public boolean isValidated() {
		return validated;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	public Set<ExaminationReport> getExaminationReport() {
		return examinationReports;
	}
	public void setExaminationReport(Set<ExaminationReport> examinationReports) {
		this.examinationReports = examinationReports;
	}
	public Set<ExaminationReport> getExaminationReports() {
		return examinationReports;
	}
	public void setExaminationReports(Set<ExaminationReport> examinationReports) {
		this.examinationReports = examinationReports;
	}
	public Nurse getNurse() {
		return nurse;
	}
	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}
	
	
	
	
}
