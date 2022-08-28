package jpa.dto;

import java.sql.Date;

import jpa.model.Examination;

public class ExaminationDTO {

	private Long id;
	private Date date;
	private Integer startTime;
	private Integer endTime;
	private double price;
	private Boolean accepted;
	private Boolean operation;
	private PatientDTO patient;
	private ClinicDTO clinic;
	private ExaminationTypeDTO type;
	private DoctorDTO doctor;
	private MedicalRoomDTO room;
	
	
	public ExaminationDTO() {}
	
	public ExaminationDTO(Examination examination) {
		if(examination.getPatient()==null  /*|| examination.getClinic()==null*/){
			this.id=examination.getId();
			this.date=examination.getDate();
			this.startTime = examination.getStartTime();
			this.endTime=examination.getEndTime();
			this.price=examination.getPrice();
			this.accepted = examination.getAccepted();
			this.operation = examination.getOperation();
			this.patient=new PatientDTO();
			this.clinic=new ClinicDTO();
			this.type = new ExaminationTypeDTO(examination.getType());
			this.doctor = new DoctorDTO(examination.getDoctor());
			if(examination.getRoom() != null) {
				this.room = new MedicalRoomDTO(examination.getRoom());
			}
			
		}
		else{
			this.id=examination.getId();
			this.date=examination.getDate();
			this.startTime = examination.getStartTime();
			this.endTime=examination.getEndTime();
			this.price=examination.getPrice();
			this.accepted = examination.getAccepted();
			this.operation = examination.getOperation();
			this.patient=new PatientDTO(examination.getPatient());
			this.clinic=new ClinicDTO();
			this.type = new ExaminationTypeDTO(examination.getType());
			this.doctor = new DoctorDTO(examination.getDoctor());
			if(examination.getRoom() != null) {
				this.room = new MedicalRoomDTO(examination.getRoom());
			}
		}
	}
	
	public ExaminationDTO(Long id, Date date, Integer startTime, Integer endTime, double price, Boolean accepted, Boolean operation) {
		super();
		this.id = id;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
		this.accepted = accepted;
		this.operation = operation;
		this.patient = new PatientDTO();
		this.clinic = new ClinicDTO();
		this.type = new ExaminationTypeDTO();
		this.doctor = new DoctorDTO();
		this.room = new MedicalRoomDTO();

	}
	
	
	
	public ExaminationDTO(Long id, Date date, Integer startTime, Integer endTime, double price, Boolean accepted, Boolean operation, PatientDTO patient,ClinicDTO clinic, DoctorDTO doctor) {
		super();
		this.id = id;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
		this.accepted = accepted;
		this.operation = operation;
		this.patient = patient;
		this.clinic = clinic;
		this.doctor = doctor;
		/*
		this.type = type;
		this.room = room;
		*/
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public Boolean getOperation() {
		return operation;
	}

	public void setOperation(Boolean operation) {
		this.operation = operation;
	}

	public ExaminationTypeDTO getType() {
		return type;
	}

	public void setType(ExaminationTypeDTO type) {
		this.type = type;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

	public DoctorDTO getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorDTO doctor) {
		this.doctor = doctor;
	}

	public MedicalRoomDTO getRoom() {
		return room;
	}

	public void setRoom(MedicalRoomDTO room) {
		this.room = room;
	}
	
	
	
}
