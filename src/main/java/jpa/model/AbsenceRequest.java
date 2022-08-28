package jpa.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class AbsenceRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "startingdate")
	private Date startingDate;
	
	@Column(name = "endingdate")
	private Date endingDate;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Doctor doctor;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Nurse nurse;
	
	public AbsenceRequest() {}
	
	public AbsenceRequest(Date startingDate, Date endingDate) {
		super();
		this.startingDate = startingDate;
		this.endingDate = endingDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	public Date getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}
	
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}

	
	
}
