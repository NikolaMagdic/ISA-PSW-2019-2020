package jpa.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jpa.dto.ExaminationDTO;
import jpa.model.Doctor;
import jpa.model.Examination;
import jpa.model.ExaminationType;
import jpa.model.MedicalRoom;
import jpa.model.Occupation;
import jpa.model.Patient;
import jpa.service.DoctorService;
import jpa.service.EmailService;
import jpa.service.ExaminationService;
import jpa.service.ExaminationTypeService;
import jpa.service.MedicalRoomService;
import jpa.service.OccupationService;
import jpa.service.PatientService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8080"},allowCredentials= "true")
@RequestMapping(value = "api/examinations")
@EnableScheduling
public class ExaminationController {
	private Logger logger = LoggerFactory.getLogger(ExaminationController.class);
	@Autowired
	private ExaminationService examinationService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ExaminationTypeService examinationTypeService;

	@Autowired
	private OccupationService occupationService;
	@Autowired
	private MedicalRoomService medicalRoomService;
	

	@Autowired
	private DoctorService doctorService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<ExaminationDTO>> getAllExaminations(HttpSession Session){
		List<Examination> examinations = examinationService.findAll();
		
		//convert examinations to DTOs
		List<ExaminationDTO> examinationsDTO = new ArrayList<>();
		for (Examination e : examinations) {
			examinationsDTO.add(new ExaminationDTO(e));
		}
		
		return new ResponseEntity<>(examinationsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/nonReserved")
	public ResponseEntity<List<ExaminationDTO>> getAllNonReservedExaminations(){
		
		List<Examination> examinations = examinationService.findAll();
		
		//convert examinations to DTOs
		List<ExaminationDTO> examinationsDTO = new ArrayList<>();
		for (Examination e : examinations) {
			if(e.getPatient()==null){
			examinationsDTO.add(new ExaminationDTO(e));
			}
		}
		System.out.println(examinationsDTO.size());
		return new ResponseEntity<>(examinationsDTO, HttpStatus.OK);

	}
	
	
	@GetMapping(value = "/allByPatient")
	public ResponseEntity<List<ExaminationDTO>> getAllExaminationssPatient(HttpSession Session){
		List<Examination> examinations = examinationService.findAll();
		System.out.println("Atribut sesije: " + Session.getAttribute("id"));
		//convert examinations to DTOs
		List<ExaminationDTO> examinationsDTO = new ArrayList<>();
	
		for (Examination e : examinations) {
			if(e.getPatient() != null && e.getAccepted()){
				if(Session.getAttribute("id")==e.getPatient().getId() ){
	
					examinationsDTO.add(new ExaminationDTO(e));
				}
			}

		}
		
		return new ResponseEntity<>(examinationsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/allByClinic")
	public ResponseEntity<List<ExaminationDTO>> getAllExaminationss(HttpSession Session){
		List<Examination> examinations = examinationService.findAll();
		
		//convert examinations to DTOs
		List<ExaminationDTO> examinationsDTO = new ArrayList<>();
		System.out.println(Session.getAttribute("selectedClinicId"));
		for (Examination e : examinations) {
			if(Session.getAttribute("selectedClinicId")==e.getClinic().getId()){
				if(e.getPatient()==null){
				examinationsDTO.add(new ExaminationDTO(e));
				}
			}
		}
		
		return new ResponseEntity<>(examinationsDTO, HttpStatus.OK);
	}
	
	// Changing parameters of examination when it is confirmed by administrator 
	@GetMapping(value = "/acceptRequestForExamination/{id}/{roomId}") 
	public ResponseEntity<Void> acceptExamination(@PathVariable Long id, @PathVariable Long roomId){ 
		Examination examination = examinationService.findOne(id); 
		 
		if(examination != null) { 
			examination.setAccepted(true); 
			MedicalRoom room  = medicalRoomService.findOne(roomId); 
			examination.setRoom(room); 
			examination = examinationService.save(examination); 
			return new ResponseEntity<>(HttpStatus.OK); 
		}else{ 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} 
	}
	
	// Sending e-mail to doctor and patient when examination is accepted 
		@GetMapping(value = "/examinationAccepted/{id}") 
		public ResponseEntity<Void> examinationAccepted(@PathVariable Long id){ 
			Examination examination = examinationService.findOne(id); 
			 
			if(examination != null) { 
				try { 
					emailService.sendNotificationExaminationDoctor(examination); 
				} catch(Exception e) { 
					logger.info("Error while sending email!"); 
				} 
				try { 
					emailService.sendNotificationExaminationPatient(examination); 
				} catch(Exception e) { 
					logger.info("Error while sending email!"); 
				} 
				return new ResponseEntity<>(HttpStatus.OK); 
			}else{ 
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
			} 
		}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ExaminationDTO> getExamination(@PathVariable Long id) {

		Examination examination = examinationService.findOne(id);

		// examination must exist
		if (examination == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ExaminationDTO(examination), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<ExaminationDTO> saveExamination(@RequestBody ExaminationDTO examinationDTO,HttpSession Session) {
		
		System.out.println("test ulaz");
		// a new examination must have examination type defined
		if(examinationDTO.getType() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		ExaminationType examinationType = examinationTypeService.findOne(examinationDTO.getType().getId());
		
		if(examinationType == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		// Examination can't exist without doctor, doctor can be changed
		if(examinationDTO.getDoctor() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Doctor doctor = doctorService.findOne(examinationDTO.getDoctor().getId());
		
		if(doctor == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Patient patient = patientService.findOne(examinationDTO.getPatient().getId());
		
		
		Examination examination = new Examination();
		examination.setId(null);
		examination.setDate((Date) examinationDTO.getDate());
		examination.setStartTime(examinationDTO.getStartTime());
		examination.setEndTime(examinationDTO.getEndTime());
		examination.setPrice(examinationDTO.getPrice());
		examination.setAccepted(examinationDTO.getAccepted());
		examination.setOperation(examinationDTO.getOperation());
		examination.setType(examinationType);
		examination.setDoctor(doctor);
		
		if(patient != null) {
			examination.setPatient(patient);
		}
		
		examination = examinationService.save(examination);
		return new ResponseEntity<>(new ExaminationDTO(examination), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/patientCreate/{id}/{idType}")
	public ResponseEntity<ExaminationDTO> save2Examination(@PathVariable Long id,@PathVariable Long idType,@RequestBody ExaminationDTO examinationDTO,HttpSession Session) {
		
		System.out.println("test ulaz1");

		// a new examination must have examination type defined
		if(idType == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		ExaminationType examinationType = examinationTypeService.findOne(idType);
		System.out.println(examinationType);
		if(examinationType == null) {
			
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// Examination can't exist without doctor, doctor can be changed
		if(id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Doctor doctor = doctorService.findOne(id);
		
		if(doctor == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		System.out.println(Session.getAttribute("id"));
		Long idd=(Long)Session.getAttribute("id");
		Patient patient = patientService.findOne(idd);
		
		
		Examination examination = new Examination();
		examination.setId(null);
		examination.setDate((Date) examinationDTO.getDate());
		examination.setStartTime(examinationDTO.getStartTime());
		examination.setEndTime(examinationDTO.getEndTime());
		examination.setPrice(examinationDTO.getPrice());
		examination.setAccepted(examinationDTO.getAccepted());
		examination.setOperation(examinationDTO.getOperation());
		examination.setType(examinationType);
		examination.setDoctor(doctor);
		
		if(patient != null) {
			examination.setPatient(patient);
			System.out.println(examination.getPatient().getName());
		}
		System.out.println(examination.getPatient().getName());
		examination = examinationService.save(examination);
		return new ResponseEntity<>(new ExaminationDTO(examination), HttpStatus.CREATED);
	}
	
	
	@GetMapping(value = "/sendVerificationMail/{id1}")
	public ResponseEntity<Void> acceptPatient(@PathVariable Long id1,HttpSession Session){
	
		Patient patient=patientService.findOne((Long)Session.getAttribute("id"));
		Examination examination=examinationService.findOne(id1);
		
		if(patient!=null){
			
			// slanje mejla za validaciju
			try {
				emailService.sendNotificaitionAsyncReserve(patient,examination);
			}catch( Exception e ){
				logger.info("Greska prilikom slanja emaila: " + e.getMessage());
			}
			
			patient.setAccepted(true);
			patient = patientService.save(patient);
			System.out.println("Stanje sada: "+ patient.isValidated());
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
		
	}
	
	
	
	@GetMapping(value = "reserve/{id1}")
	public ResponseEntity<ExaminationDTO> updateExamination(@PathVariable Long id1,HttpSession Session ) {

		Examination examination = examinationService.findOne(id1);
		Patient patient = patientService.findOne((Long)Session.getAttribute("id"));
		if (examination == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (patient == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		examination.setPatient(patient);
		examination = examinationService.save(examination);
		System.out.println(examination.getPatient().getName());
		return new ResponseEntity<>(new ExaminationDTO(examination), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteExamination(@PathVariable Long id) {

		Examination examination = examinationService.findOne(id);

		if (examination != null) {
			examinationService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// Sending email notification to clinic administrator when doctor wants to schedule request
	@GetMapping(value = "/send/{id}")
	public ResponseEntity<Void> acceptRequest(@PathVariable Long id){
		Examination examination = examinationService.findOne(id);
		
		if(examination != null) {
			try {
				emailService.sendRequestForSchedulingExamination(examination);
			} catch(Exception e) {
				logger.info("Error while sending email!");
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// Sending email notification to clinic administrator when doctor wants to schedule request
	@GetMapping(value = "/acceptRequestForOperation/{id}")
	public ResponseEntity<Void> acceptOperation(@PathVariable Long id){
		Examination examination = examinationService.findOne(id);
		
		if(examination != null) {
			examination.setAccepted(true);
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/changeDateAndTime/{id}/{date}/{startingSum}/{endingSum}")
	public ResponseEntity<Void> changeDateAndTime(@PathVariable Long id, @PathVariable Date date,@PathVariable Integer startingSum, @PathVariable Integer endingSum){
		Examination examination = examinationService.findOne(id);
		
		examination.setDate(date);
		examination.setStartTime(startingSum);
		examination.setEndTime(endingSum);
		
		examination = examinationService.save(examination);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	// Automatic assign rooms for pending examination requests
	//Currently this method is called on every hour - consider changing that if necessary
	@Scheduled(initialDelayString = "${schedule.initialdelay}", fixedDelayString = "${schedule.fixeddelay}")
	public void scheduledAccept() {
		System.out.println("Scheduled task started.");
		List<Examination> examinations = examinationService.findAll();
		for(Examination e : examinations) {
			if(e.getPatient() != null && !e.getOperation()) {
				// If there are pending requests
				if(!e.getAccepted()) {
					Date date = e.getDate();
					Integer startingSum = e.getStartTime();
					Integer endingSum = e.getEndTime();
					List<MedicalRoom> medicalRooms = medicalRoomService.findAll();
					for(MedicalRoom room : medicalRooms) {
						boolean free = true;
						Set<Occupation> occupations = room.getOccupations();
						for(Occupation oc : occupations) {
							if( !(!oc.getDate().equals(date) || endingSum <= oc.getPocetniTrenutak() ||  startingSum >= oc.getKrajnjiTrenutak())) {  
								free = false;
							}
						}
						if(free) {
							System.out.println("Dodeljena je soba za pregled automatski");
							e.setAccepted(true);
							e.setRoom(room);
							e = examinationService.save(e);
							// Evident examination in doctors work calendar and book a room
							Doctor d = e.getDoctor();
							Occupation oc1 = new Occupation(date, startingSum, endingSum);
							oc1.setDoctor(d);
							oc1 = occupationService.save(oc1);
							Occupation oc2 = new Occupation(date, startingSum, endingSum);
							oc2.setMedicalRoom(room);
							oc2 = occupationService.save(oc2);
							// Sending e-mail
							try {
								emailService.sendNotificationExaminationDoctor(e);
								emailService.sendNotificationExaminationPatient(e);
							} catch(Exception ex) {
								logger.info("Error while sending email!");
							}
							break;	
						}
					}
				}
			}
		}
	}
	
}
