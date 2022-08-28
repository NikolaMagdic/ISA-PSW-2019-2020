package jpa.dto;

import jpa.model.Nurse;

public class NurseDTO {

private Long id;
	
	private String name;
	private String surname;
	private String email;
	private String password;
	private String adress;
	private String city;
	private String state;
	private int phone;
	
	
	
	public NurseDTO(String name, String surname, String email, String password, String adress, String city, String state,
			int phone) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.adress = adress;
		this.city = city;
		this.state = state;
		this.phone = phone;
	}
	
	public NurseDTO(Nurse n) {
		super();
		this.id = n.getId();
		this.name = n.getName();
		this.surname = n.getSurname();
		this.email = n.getEmail();
		this.password = n.getPassword();
		this.adress = n.getAdress();
		this.city = n.getCity();
		this.state = n.getState();
		this.phone = n.getPhone();
	}
	
	public NurseDTO() {
		
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
}
