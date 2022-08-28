package jpa.model;


import java.util.HashSet;
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
import javax.persistence.OneToOne;


@Entity
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "surname")
	private String surname;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "adress", nullable = false)
	private String adress;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "phone")
	private int phone;
	@Column(name = "lbo")
	private int lbo; // licni broj osiguranika
	@Column(name="validated")
	private boolean validated;
	@Column(name="accepted", nullable = false)
	private boolean accepted;
	
	@Column(name = "height")
	private Integer height;
	@Column(name = "weight")
	private Integer weight;
	
	@Column(name = "bloodType")
	private String bloodType;
	
	@ManyToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Examination> examinations = new HashSet<Examination>();
	
	

	@ManyToMany(mappedBy = "patients", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Clinic> clinics = new HashSet<Clinic>();
	@ManyToMany(mappedBy = "patients", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Doctor> doctors = new HashSet<Doctor>();
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<ExaminationReport> examinationReport = new HashSet<ExaminationReport>();
	
	//@ManyToMany
	//@JoinTable(name = "diagnosed", joinColumns = @JoinColumn(name = "diagnosis_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
	//private Set<Diagnosis> diagnosiss = new HashSet<Diagnosis>();
	//Need to be added after all of the classes have been done correctly
	
	// zbog Spring Security-ja
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
	
	public Patient(){
		super();
		this.validated = false;
	}
	
	// Konstruktor, ali bez zato sto bi on morao sam da se generise
	public Patient(String name, String surname, String email, String password, String adress, String city, String state,
			int phone, int lbo) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.adress = adress;
		this.city = city;
		this.state = state;
		this.phone = phone;
		this.lbo = lbo;
		this.validated = false;
		this.accepted = false;
	}
	
	public Patient(Long id, String name, String surname, String email, String password, String adress, String city,
			String state, int phone, int lbo, boolean validated, boolean accepted, int height, int weight,
			String bloodType) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.adress = adress;
		this.city = city;
		this.state = state;
		this.phone = phone;
		this.lbo = lbo;
		this.validated = validated;
		this.accepted = accepted;
		this.height = height;
		this.weight = weight;
		this.bloodType = bloodType;
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public int getLbo() {
		return lbo;
	}
	public void setLbo(int lbo) {
		this.lbo = lbo;
	}

	public Set<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(Set<Examination> examinations) {
		this.examinations = examinations;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}


	public Set<ExaminationReport> getExaminationReport() {
		return examinationReport;
	}

	public void setExaminationReport(Set<ExaminationReport> examinationReport) {
		this.examinationReport = examinationReport;
	}

	public Set<Clinic> getClinics() {
		return clinics;
	}

	public void setClinics(Set<Clinic> clinics) {
		this.clinics = clinics;
	}

	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password="
				+ password + ", adress=" + adress + ", city=" + city + ", state=" + state + ", phone=" + phone
				+ ", lbo=" + lbo + ", validated=" + validated + ", accepted=" + accepted + ", height=" + height
				+ ", weight=" + weight + ", bloodType=" + bloodType + ", examinations=" + examinations + ", clinics="
				+ clinics + ", doctors=" + doctors + ", examinationReport=" + examinationReport + ", user=" + user
				+ "]";
	}
	
	
}
