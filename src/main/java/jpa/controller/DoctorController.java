package jpa.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jpa.dto.AbsenceRequestDTO;
import jpa.dto.DoctorDTO;
import jpa.dto.OccupationDTO;
import jpa.model.AbsenceRequest;
import jpa.model.Authority;
import jpa.model.Clinic;
import jpa.model.Doctor;
import jpa.model.Examination;
import jpa.model.MedicalRoom;
import jpa.model.Occupation;
import jpa.model.Patient;
import jpa.model.User;
import jpa.security.TokenUtils;
import jpa.service.AuthorityService;
import jpa.service.ClinicService;
import jpa.service.DoctorService;
import jpa.service.EmailService;
import jpa.service.ExaminationService;
import jpa.service.MedicalRoomService;
import jpa.service.OccupationService;
import jpa.service.PatientService;
import jpa.service.UserService;

@EnableScheduling
@RestController
@RequestMapping(value = "api/doctors")
public class DoctorController {

	
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private ExaminationService examinationService;
	
	@Autowired
	private MedicalRoomService medicalRoomService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OccupationService occupationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	private Logger logger = LoggerFactory.getLogger(DoctorController.class);
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<DoctorDTO>> getAllDoctors(){
		List<Doctor> doctors = doctorService.findAll();
		
		//convert doctors to DTOs
		List<DoctorDTO> doctorsDTO = new ArrayList<>();
		for (Doctor d : doctors) {
			doctorsDTO.add(new DoctorDTO(d));
		}
		
		return new ResponseEntity<>(doctorsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/login/{id}")
	public ResponseEntity<DoctorDTO> loginDoctor(@PathVariable Long id, HttpSession Session){
		Doctor doctor = doctorService.findOne(id);
		
		if(doctor == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Session.setAttribute("role", "DOCTOR");
		Session.setAttribute("id", id);
		
		return new ResponseEntity<>(new DoctorDTO(doctor),HttpStatus.OK);
	}

	@PreAuthorize("hasRole('DOCTOR')")
	@GetMapping(value = "/get-doctor", produces = "application/json")
	public ResponseEntity<DoctorDTO> getDoctor(HttpServletRequest request) {
		String jwtToken = this.tokenUtils.getToken(request);
		String username = tokenUtils.getUsernameFromToken(jwtToken);
		
		Doctor doctor = doctorService.findOneByEmail(username);
		DoctorDTO doctorDTO = new DoctorDTO(doctor);
		return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
	}
	
	// prima id od examinationa za koji bi trebalo da se vezu sale
	@GetMapping(value = "/freeDoctors/{idExamination}")
	public ResponseEntity<List<DoctorDTO>>getFreeDoctors(@PathVariable Long idExamination){
		
		Examination examination = examinationService.findOne(idExamination);
		
		List<Doctor> doctors = doctorService.findAll();
		
		//convert medical rooms to DTO
		List<DoctorDTO> doctorsDTO = new ArrayList<>();
		
		for(Doctor doctor : doctors) {
			boolean slobodna = true;
				
			// izmeni ako treba samo za odredjene klinike
			//if(room.getOperational()) {
				Set<Occupation> occupations = doctor.getOccupations();
				for(Occupation oc : occupations) { 
					System.out.println("Usao u occupatione");
					if( !(!oc.getDate().equals(examination.getDate()) || examination.getEndTime() <= oc.getPocetniTrenutak() ||  examination.getStartTime() >= oc.getKrajnjiTrenutak())) { 
						
						slobodna = false;
					} 
				}
			//}
			
			if(slobodna) {
				doctorsDTO.add(new DoctorDTO(doctor));
			}
		}
			
		return new ResponseEntity<>(doctorsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')") //izmeni da mogu da doprem i kada sam administrator
	@GetMapping(value = "/allDoctorsOfPatient")
	public ResponseEntity<List<DoctorDTO>> getAllDoctorsPatientHasBeen(HttpServletRequest request) {
		System.out.println("DOKTORIIIII");
		String jwtToken = this.tokenUtils.getToken(request);
		String username = tokenUtils.getUsernameFromToken(jwtToken);
		
		List<Examination> examinations = examinationService.findAll();
		Patient p = patientService.findOneByEmail(username);
		Long idOfPatient = p.getId();
		
		List<DoctorDTO> doctorDTO = new ArrayList<>();
		
		for (Examination e : examinations) {
			if(e.getPatient() != null){
			if(e.getPatient().getId() == (idOfPatient)){
				boolean vecPostoji = false;
				for(DoctorDTO dDTO : doctorDTO){
					if(dDTO.getId() == e.getClinic().getId()){
						vecPostoji = true;
					}	}
					for(Doctor dd : p.getDoctors()){
						System.out.println("udjes ovde?");
						if(e.getClinic().getId() == dd.getId()){
							vecPostoji = true;
						}
					}
				
				if(vecPostoji == false){
					doctorDTO.add(new DoctorDTO(e.getDoctor()));
					p.getDoctors().add(e.getDoctor());
				}
			}}
		}
	
		return new ResponseEntity<>(doctorDTO, HttpStatus.OK);

	}

	// proveri detaljno da li radi kako treba
	@GetMapping(value = "/freeDoctorsForOccupation/{date}/{startingSum}/{endingSum}")
	public ResponseEntity<List<DoctorDTO>>getDoctorsForOccupation(@PathVariable Date date,@PathVariable Integer startingSum, @PathVariable Integer endingSum){
		
		//Examination examination = examinationService.findOne(idExamination);
		
		List<Doctor> medicalRooms = doctorService.findAll();
		
		//convert medical rooms to DTO
		List<DoctorDTO> medicalRoomsDTO = new ArrayList<>();
		
		for(Doctor room : medicalRooms) {
			boolean slobodna = true;
			// IZMENIIII da bude samo za operacione sale
			//if(room.getOperational()) {
				Set<Occupation> occupations = room.getOccupations();
				for(Occupation oc : occupations) { 
					
					if( !(!oc.getDate().equals(date) || endingSum <= oc.getPocetniTrenutak() ||  startingSum >= oc.getKrajnjiTrenutak())) { // 
						
						System.out.println("Skontao je da je isto");
						slobodna = false;
					} 
				}
			//}
			
			if(slobodna) {
				medicalRoomsDTO.add(new DoctorDTO(room));
			}
		}
			
		return new ResponseEntity<>(medicalRoomsDTO, HttpStatus.OK);
	}
	

	@GetMapping(value = "/freeDoctorsForOccupation/{doctorsId}/{date}")
	public ResponseEntity<List<OccupationDTO>>getDoctorsOccupationForDate(@PathVariable Long doctorsId, @PathVariable Date date){
		
		List<Occupation> occupations = occupationService.findAll();
		List<OccupationDTO> occupationsDTO = new ArrayList<>();
		
		if(occupations == null) {
			System.out.println("Nema ni jednog occupationa");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
		
		
		for(Occupation oc : occupations) {
			if(oc.getDoctor() != null) {
				if(oc.getDoctor().getId() == doctorsId && oc.getDate().equals(date)) {
					occupationsDTO.add(new OccupationDTO(oc));
				}
			}
		}
			
		return new ResponseEntity<>(occupationsDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/bookDoctor/{id}/{medicalRoomId}/{examinationId}", consumes = "application/json")
	public void bookDoctor(@PathVariable Long id,@PathVariable Long medicalRoomId, @PathVariable Long examinationId, @RequestBody Occupation occupationDTO){
		
		Doctor doctor = doctorService.findOne(id);
		MedicalRoom medRoom = medicalRoomService.findOne(medicalRoomId);
		Examination examination = examinationService.findOne(examinationId);
		
		Occupation oc = new Occupation(occupationDTO.getDate(), occupationDTO.getPocetniTrenutak(), occupationDTO.getKrajnjiTrenutak());
		oc.setMedicalRoom(medRoom);
		oc.setExamination(examination);
		oc.setDoctor(doctor);
		oc = occupationService.save(oc);
				
			
		return ;
	}
	
		// stavljen da se aktivira svaki dan u 19h
		@Scheduled(cron = "0 47 0 * * ?")
		@GetMapping(value = "/bookDoctorAndRoomForTime")
		public void bookAllExaminations() {
			List<Examination> examinations = examinationService.findAll();
			for(Examination e : examinations) {
				if(e.getOperation())
					bookDoctorAndMedicalRoomByTime(e);
			}
		}
	

		public void bookDoctorAndMedicalRoomByTime(Examination e) {

					if(e.getOccupations().isEmpty()) {
						// trazim slobodnog doktora
						List<Doctor> doctors = doctorService.findAll();
						Doctor freeDoctor = null;
						for(Doctor doc : doctors) {
			
								if(freeDoctor == null) {
									Set<Occupation> occupations = doc.getOccupations();
									if(occupations.isEmpty()) {
										freeDoctor = doc;
									}else { 
										Boolean slobodan = true;
										for(Occupation oc : occupations) { 
											System.out.println("Usao u occupatione");
											if( !(!oc.getDate().equals(e.getDate()) || e.getEndTime() <= oc.getPocetniTrenutak() ||  e.getStartTime() >= oc.getKrajnjiTrenutak())) { 
												
												slobodan = false;
											} 
										}
										if(slobodan) {
											freeDoctor = doc;
										}
									}
								}
						}
						
						// trazim slobodnu sobu
						List<MedicalRoom> medRooms = medicalRoomService.findAll();
						MedicalRoom freeMedRoom = null;
						for(MedicalRoom medRoom : medRooms) {
			
								if(freeMedRoom == null) {
									Set<Occupation> occupations = medRoom.getOccupations();
									if(occupations.isEmpty()) {
										freeMedRoom = medRoom;
									} else {
										Boolean slobodna = true;
										for(Occupation oc : occupations) { 
											System.out.println("Usao u occupatione");
											if( !(!oc.getDate().equals(e.getDate()) || e.getEndTime() <= oc.getPocetniTrenutak() ||  e.getStartTime() >= oc.getKrajnjiTrenutak())) { 
												
												slobodna = false;
											} 
										}
										if(slobodna) {
											freeMedRoom = medRoom;
										}
									}
								}
						}
						
						if(freeDoctor != null && freeMedRoom != null) {
							// znam da imam jednog slobodnog doktora i jednu slobodnu sobu
							Occupation oc = new Occupation(e.getDate(), e.getStartTime(), e.getEndTime());
							oc.setMedicalRoom(freeMedRoom);
							oc.setExamination(e);
							oc.setDoctor(freeDoctor);
							oc = occupationService.save(oc);
						} else{
							int examinationStartTime = e.getStartTime();
							int examinationEndTime = e.getEndTime();
							examinationEndTime += 60; // probam da ga pomerim za sat vremena
							
							// pokusam da pomerim za kasnije vreme tog istog dana
							if(examinationEndTime < 1140) {
								examinationStartTime += 60; // pomeram i pocetno vreme da bude pomereno za jedan sat
								e.setStartTime(examinationStartTime);
								e.setEndTime(examinationEndTime);
								e = examinationService.save(e);
								// rekurzivno pozivam da vidim da li cu za izmenjeno vreme da nadjem nesto slobodno
								bookDoctorAndMedicalRoomByTime(e);
							} else {
								// moram da promenim datum na sutradan u 7 ujutru
								Date examinationDate = e.getDate();
								
								Date dat = e.getDate();
								dat.setDate(dat.getDate()+1);
								int duration = e.getEndTime() - e.getStartTime();
								
								e.setDate(dat);
								e.setStartTime(420);
								e.setEndTime(420 + duration);
								e = examinationService.save(e);
								
								bookDoctorAndMedicalRoomByTime(e);
							}
						}
					}
				
			}	
	
	
	
	
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DoctorDTO> getDoctor(@PathVariable Long id) {

		Doctor doctor = doctorService.findOne(id);
		
		// doctor must exist
		if (doctor == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new DoctorDTO(doctor), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<DoctorDTO> saveDoctor(@RequestBody DoctorDTO doctorDTO) {

		if(doctorDTO.getClass() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Clinic clinic = clinicService.findOne(doctorDTO.getClinic().getId());
		
		if(clinic == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		User existUser = this.userService.findByUsername(doctorDTO.getEmail());
		if(existUser != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	
		Doctor doctor = new Doctor();
		doctor.setId(222L);
		doctor.setName(doctorDTO.getName());
		doctor.setSurname(doctorDTO.getSurname());
		doctor.setEmail(doctorDTO.getEmail());
		doctor.setPassword(doctorDTO.getPassword());
		doctor.setAdress(doctorDTO.getAdress());
		doctor.setCity(doctorDTO.getCity());
		doctor.setState(doctorDTO.getState());
		doctor.setPhone(doctorDTO.getPhone());
		doctor.setWorkHourStart(doctorDTO.getWorkHourStart());
		doctor.setWorkHourFinish(doctorDTO.getWorkHourFinish());
		doctor.setClinic(clinic);
		
		doctor = doctorService.save(doctor);
		
		User newUser = new User();
		newUser.setName(doctorDTO.getName());
		newUser.setSurname(doctorDTO.getSurname());
		newUser.setUsername(doctorDTO.getEmail());
		newUser.setPassword(doctorDTO.getPassword());
		newUser.setEnabled(true);
		newUser.setLastPasswordResetDate(new Timestamp((new java.util.Date()).getTime()));
		newUser.setRole("doctor");
		List<Authority> auths = authService.findByName("ROLE_DOCTOR");
		newUser.setAuthorities(auths);
		newUser.setDoctor(doctor);
		
		this.userService.save(newUser);
		
		return new ResponseEntity<>(new DoctorDTO(doctor), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<DoctorDTO> updateDoctor(@RequestBody DoctorDTO doctorDTO) {

		// a doctor must exist
		Doctor doctor = doctorService.findOne(doctorDTO.getId());

		if (doctor == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		doctor.setName(doctorDTO.getName());
		doctor.setSurname(doctorDTO.getSurname());
		doctor.setEmail(doctorDTO.getEmail());
		doctor.setPassword(doctorDTO.getPassword());
		doctor.setAdress(doctorDTO.getAdress());
		doctor.setCity(doctorDTO.getCity());
		doctor.setState(doctorDTO.getState());
		doctor.setPhone(doctorDTO.getPhone());
		doctor.setWorkHourStart(doctorDTO.getWorkHourStart());
		doctor.setWorkHourFinish(doctorDTO.getWorkHourFinish());

		doctor = doctorService.save(doctor);
		return new ResponseEntity<>(new DoctorDTO(doctor), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {

		Doctor doctor = doctorService.findOne(id);

		if (doctor != null) {
			doctorService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{doctorId}/absencerequest")	
	public ResponseEntity<AbsenceRequestDTO> getDoctorsAbsenceRequest(@PathVariable Long doctorId){
		
		Doctor doctor = doctorService.findOne(doctorId);
		
		AbsenceRequest absenceRequest = doctor.getAbsenceRequest();
		AbsenceRequestDTO absenceRequestDTO = new AbsenceRequestDTO(absenceRequest);
		
		return new ResponseEntity<>(absenceRequestDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "rate/{id1}/{rating}")
	public ResponseEntity<DoctorDTO> rateClicinc(@PathVariable Long id1,@PathVariable double rating,HttpSession Session) {
		System.out.println("cek jel ulazis ovde???");
		
		Doctor doctor= doctorService.findOne(id1);

		//if(clinic.getGradeSum()!=0){
			doctor.setGradeNumber(doctor.getGradeNumber() + 1);
		//}
		
			doctor.setGradeSum(doctor.getGradeSum() + rating);
		Patient patient = patientService.findOne((Long)Session.getAttribute("id"));
		patient.getDoctors().add(doctor);
		doctor.getPatients().add(patient);
		System.out.println(patient.getClinics().size());
		patient= patientService.save(patient);
		System.out.println(patient.getClinics().size());
		doctor = doctorService.save(doctor);
		
		
		return new ResponseEntity<>(new DoctorDTO(doctor), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/accept/{id}")
	public ResponseEntity<Void> acceptRequest(@PathVariable Long id){
		Doctor doctor = doctorService.findOne(id);
		
		if(doctor != null) {
			try {
				emailService.sendNotificationAccept(doctor);
			} catch(Exception e) {
				logger.info("Error while sending email!");
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/decline/{id}/{message}")
	public ResponseEntity<Void> declineRequest(@PathVariable Long id, @PathVariable String message){
		Doctor doctor = doctorService.findOne(id);
		
		if(doctor != null) {
			try {
				emailService.sendNotificationDecline(doctor, message);
			} catch(Exception e) {
				logger.info("Error while sending email!");
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
