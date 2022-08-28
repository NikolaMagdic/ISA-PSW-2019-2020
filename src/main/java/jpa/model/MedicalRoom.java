package jpa.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class MedicalRoom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "operational")
	private Boolean operational; //True if it is operational room, false if it is ordinary
	@Column(name = "reserved")
	private Boolean reserved;
	@Column(name = "roomCodename", nullable = false)
	private String roomCodeName;
	@Column(name = "roomNumber")
	private Integer roomNumber;
	@Column(name = "date")
	private Date date; 
	//Pocetni trenuci pregleda. Duration cemo dodavati iz objekta klase examination
	//private Set<Occupation>occupations=new HashSet<Occupation>();
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Clinic clinic;
	
	@OneToMany(mappedBy = "medicalRoom", fetch = FetchType.EAGER)
	private Set<Occupation> occupations = new HashSet<Occupation>();
	
	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Examination> examinations = new HashSet<Examination>();
	
	public MedicalRoom() {}
	
	public MedicalRoom(Boolean operational, Boolean reserved, String roomCodename, Integer roomNumber, Date date) {
		super();
		this.operational = operational;
		this.reserved = reserved;
		this.roomCodeName = roomCodename;
		this.roomNumber = roomNumber;
		this.date = date;
	}

	public void addOccupation(Occupation oc) {
		occupations.add(oc);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boolean getOperational() {
		return operational;
	}

	public void setOperational(Boolean operational) {
		this.operational = operational;
	}

	public Boolean getReserved() {
		return reserved;
	}

	public void setReserved(Boolean reserved) {
		this.reserved = reserved;
	}

	public String getRoomCodeName() {
		return roomCodeName;
	}

	public void setRoomCodeName(String roomCodename) {
		this.roomCodeName = roomCodename;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Set<Occupation> getOccupations() {
		return occupations;
	}

	public void setOccupations(Set<Occupation> occupations) {
		this.occupations = occupations;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(Set<Examination> examinations) {
		this.examinations = examinations;
	}
	
	
}
