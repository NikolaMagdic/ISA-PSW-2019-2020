package jpa.dto;

import java.sql.Date;

import jpa.model.Occupation;

public class OccupationDTO {

	private Long id;
	private Date date;
	Integer pocetniTrenutak;
	Integer krajnjiTrenutak;
	Boolean h7;
	Boolean h8;
	Boolean h9;
	Boolean h10;
	Boolean h11;
	Boolean h12;
	Boolean h13;
	Boolean h14;
	Boolean h15;
	Boolean h16;
	Boolean h17;
	
	DoctorDTO doctor;
	ExaminationDTO examination;
	MedicalRoomDTO medicalRoom;
	
	public OccupationDTO() {
		super();
	}

	public OccupationDTO(Occupation oc) {
		super();
		pocetniTrenutak = oc.getPocetniTrenutak();
		krajnjiTrenutak = oc.getKrajnjiTrenutak();
		date = (Date) oc.getDate();
		doctor = new DoctorDTO(oc.getDoctor());
		// examination = new ExaminationDTO(); // dodaj da ima i examination
		medicalRoom = new MedicalRoomDTO(oc.getMedicalRoom());
		
		if(oc.getExamination() != null) {
			examination = new ExaminationDTO(oc.getExamination());
		} else {
			examination = new ExaminationDTO();
		}
	}

	


	public OccupationDTO(Date date, Integer pocetniTrenutak, Integer krajnjiTrenutak) {
		super();
		this.date = date;
		this.pocetniTrenutak = pocetniTrenutak;
		this.krajnjiTrenutak = krajnjiTrenutak;
	}

	public void setDate(Date startingDate) {
		this.date = startingDate;
	}

	public OccupationDTO(Date date, Boolean h7, Boolean h8, Boolean h9, Boolean h10, Boolean h11, Boolean h12,
			Boolean h13, Boolean h14, Boolean h15, Boolean h16, Boolean h17) {
		super();
		this.date = date;
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
	}
	
	public OccupationDTO(Long id, Date date, Boolean h7, Boolean h8, Boolean h9, Boolean h10, Boolean h11, Boolean h12,
			Boolean h13, Boolean h14, Boolean h15, Boolean h16, Boolean h17) {
		super();
		this.id = id;
		this.date = date;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDate() {
		return date;
	}


	public DoctorDTO getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorDTO doctor) {
		this.doctor = doctor;
	}

	public ExaminationDTO getExamination() {
		return examination;
	}

	public void setExamination(ExaminationDTO examination) {
		this.examination = examination;
	}

	public MedicalRoomDTO getMedicalRoom() {
		return medicalRoom;
	}

	public void setMedicalRoom(MedicalRoomDTO medicalRoom) {
		this.medicalRoom = medicalRoom;
	}
	
	
}
