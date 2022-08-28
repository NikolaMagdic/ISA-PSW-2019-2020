package jpa.dto;

import jpa.model.ClinicalAdministrator;

public class ClinicalAdministratorDTO {

private Long id;
	
	private String name;
	
	private String surname;
	
	private String email;
	
	private String password;
	
	private String adress;
	
	private String city;
	
	private String state;

	private int phone;
	
	private boolean validated;
	
	private ClinicDTO clinic;
	


	public ClinicalAdministratorDTO(String name, String surname, String email, String password, String adress,
			String city, String state, int phone) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.adress = adress;
		this.city = city;
		this.state = state;
		this.phone = phone;
		this.validated = false;
		this.clinic = new ClinicDTO();
	}

	public ClinicalAdministratorDTO(Long id, String name, String surname, String email, String password,
			String adress, String city, String state, int phone) {
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
		this.validated = false;
		this.clinic = new ClinicDTO();
	}
	
	public ClinicalAdministratorDTO(ClinicalAdministrator admin) {
		super();
		if(admin.getClinic() != null) {
			this.id = admin.getId();
			this.name = admin.getName(); 
			this.surname = admin.getSurname();
			this.email = admin.getEmail();
			this.password = admin.getPassword();
			this.adress = admin.getAdress();
			this.city = admin.getCity();
			this.state = admin.getState();
			this.phone = admin.getPhone();
			this.validated = admin.isValidated();
			this.clinic = new ClinicDTO(admin.getClinic());
		} else {
			this.id = admin.getId();
			this.name = admin.getName(); 
			this.surname = admin.getSurname();
			this.email = admin.getEmail();
			this.password = admin.getPassword();
			this.adress = admin.getAdress();
			this.city = admin.getCity();
			this.state = admin.getState();
			this.phone = admin.getPhone();
			this.validated = admin.isValidated();
			this.clinic = new ClinicDTO();
		}
	}
	public ClinicalAdministratorDTO() {
		super();
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

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	public ClinicDTO getClinic() {
		return clinic;
	}

	public void setClinic(ClinicDTO clinic) {
		this.clinic = clinic;
	}
}
