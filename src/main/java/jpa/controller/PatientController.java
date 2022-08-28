package jpa.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import jpa.dto.PatientDTO;
import jpa.model.Authority;
import jpa.model.Patient;
import jpa.model.User;
import jpa.security.TokenUtils;
import jpa.service.AuthorityService;
import jpa.service.EmailService;
import jpa.service.PatientService;
import jpa.service.UserService;


	@RestController
	@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8080" }, allowCredentials= "true")
	@RequestMapping(value = "api/patients")
public class PatientController {
		private Logger logger = LoggerFactory.getLogger(PatientController.class);

		@Autowired
		private EmailService emailService;

		@Autowired
		private PatientService patientService;

		@Autowired
		private UserService userService;
		
		@Autowired
		private AuthorityService authService;
		
		@Autowired
		private TokenUtils tokenUtils;
		
		@GetMapping(value = "/get-patient", produces = "application/json")
		public ResponseEntity<PatientDTO> getPatient(HttpServletRequest request) {
			String jwtToken = this.tokenUtils.getToken(request);
			String username = tokenUtils.getUsernameFromToken(jwtToken);
			
			Patient patient = patientService.findOneByEmail(username);
			PatientDTO patientDTO = new PatientDTO(patient);
			return new ResponseEntity<>(patientDTO, HttpStatus.OK);
		}
		
		
		@GetMapping(value = "/all")
		public ResponseEntity<List<PatientDTO>> getAllPatients() {

			List<Patient> patients = patientService.findAll();

			// convert Patients to DTOs
			List<PatientDTO> patientsDTO = new ArrayList<>();
			for (Patient p : patients) {
				patientsDTO.add(new PatientDTO(p));
			}

			return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
		}
		
		
	/*	@GetMapping(value = "/isNameTaken/{name}")
		public ResponseEntity<Boolean> getNameTaken(@PathVariable String name,HttpSession Session) {

			List<Patient> patients = patientService.findAll();

			// convert Patients to DTOs
			List<PatientDTO> patientsDTO = new ArrayList<>();
			for (Patient p : patients) {
			
			}

			return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
		}*/

		@GetMapping
		public ResponseEntity<List<PatientDTO>> getPatientsPage(Pageable page) {

			
			Page<Patient> patients = patientService.findAll(page);

		
			List<PatientDTO> patientsDTO = new ArrayList<>();
			for (Patient p : patients) {
				patientsDTO.add(new PatientDTO(p));
			}

			return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
		}

		@GetMapping(value = "/{id}")
		public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id, HttpSession Session) {
			
			Patient patient = patientService.findOne(id);

			
			if (patient == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			Session.setAttribute("role", "PATIENT");
			Session.setAttribute("id",id);
			System.out.println(Session.getAttribute("role"));
			System.out.println(Session.getAttribute("id"));
			return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.OK);
		}
		
		
		
		@GetMapping(value = "/loggedPatient")
		public ResponseEntity<PatientDTO> getLoggedPatient(HttpServletRequest request) {
			
			String jwtToken = this.tokenUtils.getToken(request);
			String username = tokenUtils.getUsernameFromToken(jwtToken);
			
			Patient patient = patientService.findOneByEmail(username);
			
			if (patient == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		
			return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.OK);
		}
		
		/*@GetMapping(value = "/login")
		public ResponseEntity<PatientDTO> getUser(@RequestBody PatientDTO patient,HttpSession Session) {
			System.out.println("ulazis u test?");
			System.out.println(patient.getId());
			Patient Patient = patientService.findOne(patient.getId());

			
			if (Patient == null) {
				System.out.println("test null ?");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			Session.setAttribute("role", "PATIENT");

			return new ResponseEntity<>(new PatientDTO(Patient), HttpStatus.OK);
		}*/
		
		

		@PostMapping(consumes = "application/json")
		public ResponseEntity<PatientDTO> savePatient(@RequestBody PatientDTO patientDTO) {

			User existUser = this.userService.findByUsername(patientDTO.getEmail());
			if(existUser != null) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			
			
			Patient patient = new Patient();
			
			System.out.println("Novi pacijent: " + patientDTO.getName());
			patient.setId(111L);
			patient.setName(patientDTO.getName());
			patient.setSurname(patientDTO.getSurname());
			patient.setEmail(patientDTO.getEmail());
			patient.setPassword(patientDTO.getPassword());
			patient.setAdress(patientDTO.getAdress());
			patient.setCity(patientDTO.getCity());
			patient.setState(patientDTO.getState());
			patient.setPhone(patientDTO.getPhone());
			patient.setLbo(patientDTO.getLbo());
			//Patient.setWeight(patientDTO.getWeight());
			//Patient.setHeight(patientDTO.getHeight());
			//Patient.setBloodType(patientDTO.getBloodType());

			patient = patientService.save(patient);
			
			// slanje emaila je prebaceno u logiku za prihvatanje naloga

			User newUser = new User();
			newUser.setName(patientDTO.getName());
			newUser.setSurname(patientDTO.getSurname());
			newUser.setUsername(patientDTO.getEmail());
			newUser.setPassword(patientDTO.getPassword());
			newUser.setEnabled(true);
			newUser.setLastPasswordResetDate(new Timestamp((new Date()).getTime()));
			newUser.setRole("patient");
			List<Authority> auths = authService.findByName("ROLE_PATIENT");
			newUser.setAuthorities(auths);
			newUser.setPatient(patient);
			
			this.userService.save(newUser);
			
			return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.CREATED);
		}

		@PostMapping(value = "/updatePatient", consumes = "application/json")
		public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO, HttpSession Session) {
			System.out.println(patientDTO.getPassword());
			// a Patient must exist
			Patient Patient = patientService.findOne(patientDTO.getId());

			if (Patient == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			Patient.setName(patientDTO.getName());
			Patient.setSurname(patientDTO.getSurname());
			Patient.setEmail(patientDTO.getEmail());
			Patient.setPassword(patientDTO.getPassword());
			Patient.setAdress(patientDTO.getAdress());
			Patient.setCity(patientDTO.getCity());
			Patient.setState(patientDTO.getState());
			Patient.setPhone(patientDTO.getPhone());
			Patient.setLbo(patientDTO.getLbo());

			Patient.setWeight(patientDTO.getWeight());
			Patient.setHeight(patientDTO.getHeight());
			Patient.setBloodType(patientDTO.getBloodType());
			
			Patient = patientService.save(Patient);
			return new ResponseEntity<>(new PatientDTO(Patient), HttpStatus.OK);
		}
		
		
		@PutMapping(value = "/updateMedicalRedord", consumes = "application/json")
		public ResponseEntity<PatientDTO> updateMedicalRecord(@RequestBody PatientDTO patientDTO) {
			
			Patient patient = patientService.findOne(patientDTO.getId());
				
			if (patient == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			patient.setWeight(patientDTO.getWeight());
			patient.setHeight(patientDTO.getHeight());
			patient.setBloodType(patientDTO.getBloodType());
			
			patient = patientService.save(patient);
			return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.OK);
		}
		

		@DeleteMapping(value = "/{id}")
		public ResponseEntity<Void> deletePatient(@PathVariable Long id) {

			Patient Patient = patientService.findOne(id);

			if (Patient != null) {
				patientService.remove(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}

		@GetMapping(value = "/findName")
		public ResponseEntity<PatientDTO> getPatientByIndex(@RequestParam String index) {

			Patient Patient = patientService.findByIndex(index);
			if (Patient == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(new PatientDTO(Patient), HttpStatus.OK);
		}

		@GetMapping(value = "/findLastName")
		public ResponseEntity<List<PatientDTO>> getPatientsByLastName(@RequestParam String lastName) {

			List<Patient> Patients = patientService.findByLastName(lastName);

			// convert Patients to DTOs
			List<PatientDTO> PatientsDTO = new ArrayList<>();
			for (Patient s : Patients) {
				PatientsDTO.add(new PatientDTO(s));
			}
			return new ResponseEntity<>(PatientsDTO, HttpStatus.OK);
		}

		@GetMapping(value = "/prezime")
		public ResponseEntity<List<PatientDTO>> pronadjiPatientePoPrezimenu(@RequestParam String lastName) {

			List<Patient> Patients = patientService.pronadjiPoPrezimenu(lastName);

			// convert Patients to DTOs
			List<PatientDTO> PatientsDTO = new ArrayList<>();
			for (Patient s : Patients) {
				PatientsDTO.add(new PatientDTO(s));
			}
			return new ResponseEntity<>(PatientsDTO, HttpStatus.OK);
		}

		@GetMapping(value = "/findFirstLast")
		public ResponseEntity<List<PatientDTO>> getPatientsByFirstNameAndLastName(@RequestParam String firstName,
				@RequestPart String lastName) {

			List<Patient> Patients = patientService.findByFirstNameAndLastName(firstName, lastName);

			// convert Patients to DTOs
			List<PatientDTO> PatientsDTO = new ArrayList<>();
			for (Patient s : Patients) {
				PatientsDTO.add(new PatientDTO(s));
			}
			return new ResponseEntity<>(PatientsDTO, HttpStatus.OK);
		}
		
		
		@GetMapping(value = "/validate/{id}")
		public ResponseEntity<Void> validatePatient(@PathVariable Long id){
		
			Patient patient=patientService.findOne(id);
			
			if(patient!=null){
				System.out.println("da li si ovde");
				patient.setValidated(true);
				patient = patientService.save(patient);
				System.out.println("Stanje sada: "+ patient.isValidated());
				return new ResponseEntity<>(HttpStatus.OK);
			}else{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
		}
		
		@GetMapping(value = "/accept/{id}")
		public ResponseEntity<Void> acceptPatient(@PathVariable Long id){
		
			Patient patient=patientService.findOne(id);
			
			
			if(patient!=null){
				
				// slanje mejla za validaciju
				try {
					emailService.sendNotificaitionAsync(patient);
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
		
		@GetMapping(value = "/nonAccepted")
		public ResponseEntity<List<PatientDTO>> getNonAcceptedPatients() {

			List<Patient> patients = patientService.findAll();

			// convert Patients to DTOs
			List<PatientDTO> patientsDTO = new ArrayList<>();
			for (Patient p : patients) {
				if(p.isAccepted() == false)
					patientsDTO.add(new PatientDTO(p));
			}

			return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
		}
		
		@PutMapping(value = "rejectPatient/{id}/{message}")
		public ResponseEntity<Void> rejectPatient(@PathVariable Long id, @PathVariable String message) {

			Patient patient = patientService.findOne(id);

			if (patient != null) {
				try {
					emailService.sendNotificaitionReject(patient, message);
				}catch( Exception e ){
					logger.info("Greska prilikom slanja emaila: " + e.getMessage());
				}
				patientService.remove(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		

	/*	@GetMapping(value = "/{PatientId}/Prescriptions")
		public ResponseEntity<List<PrescriptionDTO>> getPatientExams(@PathVariable Long PatientId) {
			Patient Patient = patientService.findOne(PatientId);
			Set<Prescription> prescriptions = Patient.getPrescription();
			List<PrescriptionDTO> examsDTO = new ArrayList<>();
			for (Prescription p : Prescription) {
				PrescriptionDTO prescriptionDTO = new ExamDTO();
				prescriptionDTO.setId(e.getId());
				prescriptionDTO.setGrade(e.getGrade());
				prescriptionDTO.setDate(e.getDate());
				prescriptionDTO.setCourse(new CourseDTO(e.getCourse()));
				prescriptionDTO.setPatient(new PatientDTO(e.getPatient()));

				examsDTO.add(prescriptionDTO);
			}
			return new ResponseEntity<>(examsDTO, HttpStatus.OK);
		}*/
		// Ovo dodati kada se za sve MODELE URADEEEEE
}

