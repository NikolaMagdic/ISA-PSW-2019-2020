package jpa.dto;

import jpa.model.Doctor;

public class DoctorDTO {
		
	private Long id;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String adress;
	private String city;
	private String state;
	private int phone;
	private double gradeSum;
	private int gradeNumber;
	private Integer workHourStart;
	private Integer workHourFinish;
	private ClinicDTO clinic;
	//private Set<Patient>patient=new HashSet<Patient>();
	// private Set<Examination> examinations=new HashSet<Examination>();
	
	public DoctorDTO() {
		
	}
		
	public DoctorDTO(Doctor doctor) {
		this.id = doctor.getId();
		this.name = doctor.getName();
		this.surname = doctor.getSurname();
		this.email = doctor.getEmail();
		this.password = doctor.getPassword();
		this.adress = doctor.getAdress();
		this.city = doctor.getCity();
		this.state = doctor.getState();
		this.phone = doctor.getPhone();
		this.workHourStart = doctor.getWorkHourStart();
		this.workHourFinish = doctor.getWorkHourFinish();
		this.clinic = new ClinicDTO(doctor.getClinic());
		this.gradeSum = doctor.getGradeSum();
		this.gradeNumber = doctor.getGradeNumber();
	}
	
	public DoctorDTO(Long id,String name, String surname, String email, String password, String adress, String city, String state,
		int phone, Integer workHourStart, Integer workHourFinish ,double gradeSum,int gradeNumber) {
		super();
		this.id=id;
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
		this.clinic = new ClinicDTO();
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
		
		public ClinicDTO getClinic() {
			return clinic;
		}


		public void setClinic(ClinicDTO clinic) {
			this.clinic = clinic;
		}
		
		/*
		public Set<Patient> getPatient() {
			return patient;
		}


		public void setPatient(Set<Patient> patient) {
			this.patient = patient;
		}

		
		public Set<Examination> getExaminations() {
			return examinations;
		}


		public void setExaminations(Set<Examination> examinations) {
			this.examinations = examinations;
		}
		*/ 
		
		
}
