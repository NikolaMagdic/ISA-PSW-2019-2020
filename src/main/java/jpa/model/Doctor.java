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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "surname", nullable = false)
	private String surname;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "adress")
	private String adress;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "workHourStart")
	private Integer workHourStart;
	
	@Column(name = "workHourFinish")
	private Integer workHourFinish;
	
	@Column( name = "gradeSum" ,nullable = true)
	private double gradeSum; 
	
	@Column (name="gradeNumber",nullable = true)
	private int gradeNumber;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Clinic clinic;
	
	@Column(name = "phone")
	private int phone;
	
	//@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private Set<Patient>patient=new HashSet<Patient>();
	
	//check if mappedBy is necessary
	@OneToMany(mappedBy="doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Examination> examinations=new HashSet<Examination>();
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
	private Set<Occupation> occupations = new HashSet<Occupation>();
	
	@ManyToMany
	@JoinTable(name = "ratedDoctor", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
	private Set<Patient> patients = new HashSet<Patient>();
	
	@OneToOne(mappedBy = "doctor", fetch = FetchType.EAGER)
	private AbsenceRequest absenceRequest;
	
	// zbog Spring Security-ja
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
	
	public Doctor() {}
	
	public Doctor(String name, String surname, String email, String password, String adress, String city, String state,
			int phone, Integer workHourStart, Integer workHourFinish, double gradeSum,int gradeNumber) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.adress = adress;
		this.city = city;
		this.state = state;
		this.phone = phone;
		this.workHourStart = workHourStart;
		this.workHourFinish = workHourFinish;
		this.gradeSum = gradeSum;
		this.gradeNumber = gradeNumber;
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

	public Integer getWorkHourStart() {
		return workHourStart;
	}

	public void setWorkHourStart(Integer workHourStart) {
		this.workHourStart = workHourStart;
	}

	public Integer getWorkHourFinish() {
		return workHourFinish;
	}

	public void setWorkHourFinish(Integer workHourFinish) {
		this.workHourFinish = workHourFinish;
	}

	public Set<Examination> getExaminations() {
		return examinations;
	}


	public void setExaminations(Set<Examination> examinations) {
		this.examinations = examinations;
	}
	
/*	public Set<Patient> getPatient() {
		return patient;
	}


	public void setPatient(Set<Patient> patient) {
		this.patient = patient;
	}

	*/
	public Clinic getClinic() {
		return clinic;
	}


	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	public AbsenceRequest getAbsenceRequest() {
		return absenceRequest;
	}

	public void setAbsenceRequest(AbsenceRequest absenceRequest) {
		this.absenceRequest = absenceRequest;
	}

	public Set<Occupation> getOccupations() {
		return occupations;
	}

	public void setOccupations(Set<Occupation> occupations) {
		this.occupations = occupations;
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

	public Set<Patient> getPatients() {
		return patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password="
				+ password + ", adress=" + adress + ", city=" + city + ", state=" + state + ", workHourStart="
				+ workHourStart + ", workHourFinish=" + workHourFinish + ", gradeSum=" + gradeSum + ", gradeNumber="
				+ gradeNumber + ", clinic=" + clinic + ", phone=" + phone + ", examinations=" + examinations
				+ ", occupations=" + occupations + ", patients=" + patients + ", absenceRequest=" + absenceRequest
				+ ", user=" + user + "]";
	}
	
	
}
