package jpa.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/*
 * Date - odredjuje dan za koji pokazujemo zauzece
 * 12 bool vrednosti, svaka pokazuje kada je zauzece aktivno\
 * Objekti klase occupation treba da se koriste za sve gde se ikakvo zauzece spominje (doktor, sobe, examination)
 * Examination treba takodje da ima objekat klase occupation kako bi moglo preko njega da se zakazuju sale i lekari 
 * */

@Entity
public class Occupation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "startingDate")
	private Date date; // dan za koji vezujemo zauzece
	// boolean vrednosti za svaki sat radnog vremena
	// oznacena vrednost znaci da je zauzece aktivno od tog trenutka,  narednih sat vremena
	// Ako zauece krece u 7h i traje 3 sata, treba da budu aktivni booleani 7,8,9
	
	@Column
	Integer pocetniTrenutak;
	@Column 
	Integer krajnjiTrenutak;
	
	@Column
	Boolean h7;
	@Column
	Boolean h8;
	@Column
	Boolean h9;
	@Column
	Boolean h10;
	@Column
	Boolean h11;
	@Column
	Boolean h12;
	@Column
	Boolean h13;
	@Column
	Boolean h14;
	@Column
	Boolean h15;
	@Column
	Boolean h16;
	@Column
	Boolean h17;

	

	// morace da ima i doktore i examinatione
	@ManyToOne(fetch = FetchType.EAGER)
	private MedicalRoom medicalRoom;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Doctor doctor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Examination examination;
	
	public Occupation() {
		
		super();
	}
	public Occupation(Date pocetak) {
		super();
		this.date = pocetak;
	}
	
	public Occupation(Date date, Integer pocetniTrenutak, Integer krajnjiTrenutak) {
		super();
		this.date = date;
		this.pocetniTrenutak = pocetniTrenutak;
		this.krajnjiTrenutak = krajnjiTrenutak;
	}
	
	public Occupation(Boolean h7, Boolean h8, Boolean h9, Boolean h10, Boolean h11, Boolean h12, Boolean h13,
			Boolean h14, Boolean h15, Boolean h16, Boolean h17, Date date) {
		super();
		this.h7 = h7;
		this.h8 = h8;
		this.h9 = h9;
		this.h10 = h10;
		this.h11 = h11;
		this.h12 = h12;
		this.h13 = h13;
		this.h14 = h14;
		this.h15 = h15;
		this.h16 = h16;
		this.h17 = h17;
		this.date = date;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getH7() {
		return h7;
	}
	public void setH7(Boolean h7) {
		this.h7 = h7;
	}
	public Boolean getH8() {
		return h8;
	}
	public void setH8(Boolean h8) {
		this.h8 = h8;
	}
	public Boolean getH9() {
		return h9;
	}
	public void setH9(Boolean h9) {
		this.h9 = h9;
	}
	public Boolean getH10() {
		return h10;
	}
	public void setH10(Boolean h10) {
		this.h10 = h10;
	}
	public Boolean getH11() {
		return h11;
	}
	public void setH11(Boolean h11) {
		this.h11 = h11;
	}
	public Boolean getH12() {
		return h12;
	}
	public void setH12(Boolean h12) {
		this.h12 = h12;
	}
	public Boolean getH13() {
		return h13;
	}
	public void setH13(Boolean h13) {
		this.h13 = h13;
	}
	public Boolean getH14() {
		return h14;
	}
	public void setH14(Boolean h14) {
		this.h14 = h14;
	}
	public Boolean getH15() {
		return h15;
	}
	public void setH15(Boolean h15) {
		this.h15 = h15;
	}
	public Boolean getH16() {
		return h16;
	}
	public void setH16(Boolean h16) {
		this.h16 = h16;
	}
	public Boolean getH17() {
		return h17;
	}
	public void setH17(Boolean h17) {
		this.h17 = h17;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public MedicalRoom getMedicalRoom() {
		return medicalRoom;
	}
	public void setMedicalRoom(MedicalRoom medicalRoom) {
		this.medicalRoom = medicalRoom;
	}
	public Integer getPocetniTrenutak() {
		return pocetniTrenutak;
	}
	public void setPocetniTrenutak(Integer pocetniTrenutak) {
		this.pocetniTrenutak = pocetniTrenutak;
	}
	public Integer getKrajnjiTrenutak() {
		return krajnjiTrenutak;
	}
	public void setKrajnjiTrenutak(Integer krajnjiTrenutak) {
		this.krajnjiTrenutak = krajnjiTrenutak;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public Examination getExamination() {
		return examination;
	}
	public void setExamination(Examination examination) {
		this.examination = examination;
	}

	
	
}
