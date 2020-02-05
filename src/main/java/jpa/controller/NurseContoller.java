package jpa.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import jpa.dto.AbsenceRequestDTO;
import jpa.dto.NurseDTO;
import jpa.modeli.AbsenceRequest;
import jpa.modeli.Nurse;
import jpa.service.EmailService;
import jpa.service.NurseService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8080" })
@RequestMapping(value = "api/nurses")
public class NurseContoller {

	@Autowired
	private NurseService nurseService;
	
	@Autowired
	private EmailService emailService;
	
	private Logger logger = LoggerFactory.getLogger(NurseContoller.class);
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<NurseDTO>> getAllNurses(){
		List<Nurse> nurses = nurseService.findAll();
		
		
		List<NurseDTO> nursesDTO = new ArrayList<>();
		for (Nurse n : nurses) {
			nursesDTO.add(new NurseDTO(n));
		}
		
		return new ResponseEntity<>(nursesDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<NurseDTO> getNurse(@PathVariable Long id) {

		Nurse nurse = nurseService.findOne(id);

		// doctor must exist
		if (nurse == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new NurseDTO(nurse), HttpStatus.OK);
	}
	
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<NurseDTO> saveNurse(@RequestBody NurseDTO nurseDTO) {

		Nurse nurse = new Nurse();
		nurse.setId(222l);
		nurse.setName(nurseDTO.getName());
		nurse.setSurname(nurseDTO.getSurname());
		nurse.setEmail(nurseDTO.getEmail());
		nurse.setPassword(nurseDTO.getPassword());
		nurse.setAdress(nurseDTO.getAdress());
		nurse.setCity(nurseDTO.getCity());
		nurse.setState(nurseDTO.getState());
		nurse.setPhone(nurseDTO.getPhone());
		
		nurse = nurseService.save(nurse);
		
		return new ResponseEntity<>(new NurseDTO(nurse), HttpStatus.CREATED);
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<NurseDTO> updateNurse(@RequestBody NurseDTO nurseDTO){
		
		Nurse nurse = nurseService.findOne(nurseDTO.getId());
		
		if(nurse == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		nurse.setName(nurseDTO.getName());
		nurse.setSurname(nurseDTO.getSurname());
		nurse.setEmail(nurseDTO.getEmail());
		nurse.setPassword(nurseDTO.getPassword());
		nurse.setAdress(nurseDTO.getAdress());
		nurse.setCity(nurseDTO.getCity());
		nurse.setState(nurseDTO.getState());
		nurse.setPhone(nurseDTO.getPhone());
		
		nurse = nurseService.save(nurse);
		return new ResponseEntity<>(new NurseDTO(nurse), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteNurse(@PathVariable Long id) {

		Nurse nurse = nurseService.findOne(id);

		if (nurse != null) {
			nurseService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/{nurseId}/absencerequest")	
	public ResponseEntity<AbsenceRequestDTO> getNersesAbsenceRequest(@PathVariable Long nurseId){
		
		Nurse nurse = nurseService.findOne(nurseId);
		
		AbsenceRequest absenceRequest = nurse.getAbsenceRequest();
		AbsenceRequestDTO absenceRequestDTO = new AbsenceRequestDTO(absenceRequest);
		
		return new ResponseEntity<>(absenceRequestDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/accept/{id}")
	public ResponseEntity<Void> acceptRequest(@PathVariable Long id){
		Nurse nurse = nurseService.findOne(id);
		
		if(nurse != null) {
			try {
				emailService.sendNotificationAcceptNurse(nurse);
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
		Nurse nurse = nurseService.findOne(id);
		
		if(nurse != null) {
			try {
				emailService.sendNotificationDeclineNurse(nurse, message);
			} catch(Exception e) {
				logger.info("Error while sending email!");
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}











