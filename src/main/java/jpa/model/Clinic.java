package jpa.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;



@Entity
public class Clinic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "adress", nullable = true)
	private String adress;
	
	@Column(name = "description", nullable = true)
	private String description;
	
	@Column(name = "gradeSum", nullable = true)
	private double gradeSum; // average is gradeSum divided by gradeNumber
	
	@Column(name = "gradeNumber", nullable = true)
	private int gradeNumber;
	
	@Column(name = "longitude")
	private double longitude;
	
	@Column(name = "latitude")
	private double latitude;
	
	
	@OneToMany(mappedBy = "clinic", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//@Column(name="Examinations", nullable = true)
	private Set<Examination> examinations = new HashSet<Examination>();
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Doctor> doctors = new HashSet<Doctor>();
	
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ClinicalAdministrator> administrators = new HashSet<ClinicalAdministrator>();
	
	//@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private Set<MedicalRoom> rooms=new HashSet<MedicalRoom>();
	@OneToMany(mappedBy = "clinic", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<MedicalRoom> rooms = new HashSet<MedicalRoom>();
	
	@ManyToMany
	@JoinTable(name = "rated", joinColumns = @JoinColumn(name = "clinic_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
	private Set<Patient> patients = new HashSet<Patient>();
	
	
	//@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private Set<Examination> examinations =new HashSet<Examination>();
	
	// one clinic can have many prescription, and one prescription can be in many clinics
	/*@ManyToMany
	@JoinTable(name = "prescripted", joinColumns = @JoinColumn(name = "clinic_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "prescription_id", referencedColumnName = "id"))
	private Set<Prescription>prescriptions=new HashSet<Prescription>();*/
	
	// needs to add price book for appointments and for operations
	// needs to have list of free terms, rooms and doctors
	
	public Clinic() {
		super();
	}

	public Clinic(String name, String adress, String description, double longitude, double latitude) {
		super();
		this.name = name;
		this.adress = adress;
		this.description = description;
		this.gradeSum = 0;
		this.gradeNumber = 1;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Clinic(String name, String adress, String description, double gradeSum, int gradeNumber, double longitude, double latitude) {
		super();
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

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getGradeSum() {
		return gradeSum;
	}

	public void setGradeSum(double gradeSum) {
		this.gradeSum = gradeSum;
	}

	public int getGradeNumber() {
		return gradeNumber;
	}

	public void setGradeNumber(int gradeNumber) {
		this.gradeNumber = gradeNumber;
	}
	
	public double getGrade() {
		if(gradeNumber > 0) {
			return gradeSum/gradeNumber;
		} else return 100;
		
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

	public Set<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(Set<Examination> examinations) {
		this.examinations = examinations;
	}

	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doktori) {
		this.doctors = doktori;
	}

	public Set<MedicalRoom> getRooms() {
		return rooms;
	}

	public void setRooms(Set<MedicalRoom> rooms) {
		this.rooms = rooms;
	}
/*
	public Set<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(Set<Examination> examinations) {
		this.examinations = examinations;
	}

	public Set<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(Set<Prescription> prescriptions) {
		this.prescriptions = prescriptions;*/
	//}

	public Set<Patient> getPatients() {
		return patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}

	
	
}
