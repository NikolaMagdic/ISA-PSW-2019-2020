package jpa.dto;

import jpa.model.Patient;

public class PatientDTO {
	private Long id;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String adress;
	private String city;
	private String state;
	private int phone;
	private int lbo;
	private boolean validated;
	private boolean accepted;
	private Integer height;
	private Integer weight;
	private String bloodType;
	
	public PatientDTO() {
		
	}
	public PatientDTO(String email,String password){
		this.email=email;
		this.password=password;
	}
	
	
	public PatientDTO(Long id2, String name2, String surname2, String email2, String password2, String adress2,
			String city2,String state2, int phone2,int lbo2) {
		
		

		this.id=id2;
		this.name=name2;
		this.surname=surname2;
		this.email=email2;
		this.password=password2;
		this.adress=adress2;
		this.city=city2;
		this.state=state2;
		this.phone=phone2;
		this.lbo=lbo2;
		this.validated = false;
		this.accepted = false;
	}
	
	public PatientDTO(Long id, String name, String surname, String email, String password, String adress, String city,
			String state, int phone, int lbo, boolean validated, boolean accepted, Integer height, Integer weight,
			String bloodType) {
		super();
		
		System.out.println("Usao je u ovaj konstrruktokwsotwrkrg");
		
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
	
	
	public PatientDTO(Patient patient) {
		
		System.out.println("Kontruktor od pacijenta kao modela");
		
		this.id=patient.getId();
		this.name=patient.getName();
		this.surname=patient.getSurname();
		this.email=patient.getEmail();
		this.password=patient.getPassword();
		this.adress=patient.getAdress();
		this.city=patient.getCity();
		this.state=patient.getState();
		this.phone=patient.getPhone();
		this.lbo=patient.getLbo();
		this.validated = patient.isValidated();
		this.accepted = patient.isAccepted();
		this.weight = patient.getWeight();
		this.height = patient.getHeight();
		this.bloodType = patient.getBloodType();
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
	
	@Override
	public String toString() {
		return "PatientDTO [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password="
				+ password + ", adress=" + adress + ", city=" + city + ", state=" + state + ", phone=" + phone
				+ ", lbo=" + lbo + ", validated=" + validated + ", accepted=" + accepted + ", height=" + height
				+ ", weight=" + weight + ", bloodType=" + bloodType + "]";
	}	
	
	
	
}
