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
import javax.persistence.OneToMany;

@Entity
public class Diagnosis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "diagnosis", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<ExaminationReport> examinationReport = new HashSet<ExaminationReport>();
	

	public Diagnosis(){
		
	}

	public Diagnosis(String name) {
		super();
		this.name = name;
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

	public Set<ExaminationReport> getExaminationReport() {
		return examinationReport;
	}

	public void setExaminationReport(Set<ExaminationReport> examinationReport) {
		this.examinationReport = examinationReport;
	}
	
	
	
	
	
	
}	
