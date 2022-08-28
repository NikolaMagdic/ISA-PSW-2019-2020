package jpa.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jpa.dto.ClinicalAdministratorDTO;
import jpa.model.Authority;
import jpa.model.Clinic;
import jpa.model.ClinicalAdministrator;
import jpa.model.User;
import jpa.security.TokenUtils;
import jpa.service.AuthorityService;
import jpa.service.ClinicService;
import jpa.service.ClinicalAdministratorService;
import jpa.service.EmailService;
import jpa.service.UserService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8080" },allowCredentials= "true")
@RequestMapping(value = "api/clinicalAdministrators")
public class ClinicalAdministratorController {

	
	private Logger logger = LoggerFactory.getLogger(PatientController.class);
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ClinicalAdministratorService service;
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<ClinicalAdministratorDTO>> getAllAdministrators() {

		List<ClinicalAdministrator> clinics = service.findAll();

		// convert clinics to DTOs
		List<ClinicalAdministratorDTO> clinicDTO = new ArrayList<>();
		for (ClinicalAdministrator c : clinics) {
			clinicDTO.add(new ClinicalAdministratorDTO(c));
		}

		return new ResponseEntity<>(clinicDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getClinicalAdministrator", produces = "application/json")
	public ResponseEntity<ClinicalAdministratorDTO> getClinicalAdministrator(HttpServletRequest request) {
		String jwtToken = this.tokenUtils.getToken(request);
		String username = tokenUtils.getUsernameFromToken(jwtToken);
		
		ClinicalAdministrator clinicalAdministrator = service.findOneByEmail(username);
		ClinicalAdministratorDTO clinicalAdministratorDTO = new ClinicalAdministratorDTO(clinicalAdministrator);
		return new ResponseEntity<>(clinicalAdministratorDTO, HttpStatus.OK);
	}

	@GetMapping(value="/login/{id}")
	public ResponseEntity<ClinicalAdministratorDTO> loginDoctor(@PathVariable Long id, HttpSession Session){

		ClinicalAdministrator admin = service.findOne(id);
		if(admin == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Session.setAttribute("role", "CLINICALADMINISTRATOR");
		Session.setAttribute("id", id);
		
		return new ResponseEntity<>(new ClinicalAdministratorDTO(admin),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('CLINICAL_ADMIN')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClinicalAdministratorDTO> getAdministrators(@PathVariable Long id) {

		System.out.println("Indeks trazenog administratora je: " + id);
		ClinicalAdministrator admin = service.findOne(id);
		
		if (admin == null) {
			System.out.println("Nisam nasao sa idejem" + id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ClinicalAdministratorDTO>(new ClinicalAdministratorDTO(admin), HttpStatus.OK);
	}
	
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<ClinicalAdministratorDTO> saveAdministrator(@RequestBody ClinicalAdministratorDTO adminDTO) {

		// novi administrator mora da ima kliniku za koju je vezan
		if(adminDTO.getClinic() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		User existUser = this.userService.findByUsername(adminDTO.getEmail());
		if(existUser != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	
		
		Clinic clinic = clinicService.findOne(adminDTO.getClinic().getId());
		
		ClinicalAdministrator admin = new ClinicalAdministrator();
		admin.setId(222);
		admin.setName(adminDTO.getName());
		admin.setSurname(adminDTO.getSurname());
		admin.setEmail(adminDTO.getEmail());
		admin.setPassword(adminDTO.getPassword());
		admin.setAdress(adminDTO.getAdress());
		admin.setCity(adminDTO.getCity());
		admin.setState(adminDTO.getState());
		admin.setPhone(adminDTO.getPhone());
		admin.setClinic(clinic);;
		
		System.out.println("********* Prosledjeno ime administratora: " + adminDTO.getName()+ " ***************");
		
		admin = service.save(admin);
		
		User newUser = new User();
		newUser.setName(adminDTO.getName());
		newUser.setSurname(adminDTO.getSurname());
		newUser.setUsername(adminDTO.getEmail());
		newUser.setPassword(adminDTO.getPassword());
		newUser.setEnabled(true);
		newUser.setLastPasswordResetDate(new Timestamp((new java.util.Date()).getTime()));
		newUser.setRole("clinical_admin");
		List<Authority> auths = authService.findByName("ROLE_CLINICAL_ADMIN");
		newUser.setAuthorities(auths);
		newUser.setClinicalAdministrator(admin);
		this.userService.save(newUser);
		
		List<ClinicalAdministrator> administratoriIzBaze = service.findAll();
		long id = 0;
		for(ClinicalAdministrator adm : administratoriIzBaze) {
			if(adm.getEmail().equals(adminDTO.getEmail())) {
				id = adm.getId();
				System.out.println("Id novog administratora je: "+id);
			}
		}
		try {
			emailService.sendNotificaitionAsync(admin); // napravi za ClinicalAdministrator !!!
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
		}
		return new ResponseEntity<>(new ClinicalAdministratorDTO(admin), HttpStatus.CREATED);
	}
	
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<ClinicalAdministratorDTO> updateAdministrator(@RequestBody ClinicalAdministratorDTO clinicDTO) {

		// a admin must exist
		ClinicalAdministrator admin = service.findOne(clinicDTO.getId());

		if (admin == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		admin.setName(clinicDTO.getName());
		admin.setSurname(clinicDTO.getSurname());
		admin.setEmail(clinicDTO.getEmail());
		admin.setPassword(clinicDTO.getPassword());
		admin.setAdress(clinicDTO.getAdress());
		admin.setCity(clinicDTO.getCity());
		admin.setState(clinicDTO.getState());
		admin.setPhone(clinicDTO.getPhone());
		
		admin = service.save(admin);
		return new ResponseEntity<>(new ClinicalAdministratorDTO(admin), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/validate/{id}")
	public ResponseEntity<Void> validateAdministrator(@PathVariable Long id) {

		ClinicalAdministrator admin = service.findOne(id);
		
		if (admin != null) {
			admin.setValidated(true);
			admin = service.save(admin);
			System.out.println("Admin sa id-jem: "+id+" je valiran");
			System.out.println("Njegovo stanje je sada: "+admin.isValidated());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			System.out.println("Prosledjeno je, ali je admin null");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id) {

		ClinicalAdministrator admin = service.findOne(id);

		if (admin != null) {
			service.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
