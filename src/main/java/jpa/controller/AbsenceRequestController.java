package jpa.controller;

import java.util.ArrayList;
import java.util.List;

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
import jpa.dto.DoctorDTO;
import jpa.dto.NurseDTO;
import jpa.model.AbsenceRequest;
import jpa.model.Doctor;
import jpa.model.Nurse;
import jpa.service.AbsenceRequestService;
import jpa.service.DoctorService;
import jpa.service.NurseService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8080" },allowCredentials= "true")
@RequestMapping(value = "api/absencerequests")
public class AbsenceRequestController {

	@Autowired
	private AbsenceRequestService absenceRequestService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private NurseService nurseService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<AbsenceRequestDTO>>getAllAbsenceRequests(){
		List<AbsenceRequest> absenceRequests = absenceRequestService.findAll();
		
		//convert absence request to DTO
		List<AbsenceRequestDTO> absenceRequestsDTO = new ArrayList<>();
			for (AbsenceRequest ar : absenceRequests) {
				absenceRequestsDTO.add(new AbsenceRequestDTO(ar));
			}
		return new ResponseEntity<>(absenceRequestsDTO, HttpStatus.OK);
	}
	

	@GetMapping(value = "/{id}")
	public ResponseEntity<AbsenceRequestDTO> getAbsenceRequest(@PathVariable Long id) {

		AbsenceRequest absenceRequest = absenceRequestService.findOne(id);

		// absence request must exist
		if (absenceRequest == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new AbsenceRequestDTO(absenceRequest), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<AbsenceRequestDTO> saveAbsenceRequest(@RequestBody AbsenceRequestDTO absenceRequestDTO) {

		if((absenceRequestDTO.getDoctor() == null) && (absenceRequestDTO.getNurse() == null)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		AbsenceRequest absenceRequest = new AbsenceRequest();
		//absenceRequest.setId((0));
		absenceRequest.setStartingDate(absenceRequestDTO.getStartingDate());
		absenceRequest.setEndingDate(absenceRequestDTO.getEndingDate());
		
		if(absenceRequestDTO.getDoctor() != null) {
			Doctor doctor = doctorService.findOne(absenceRequestDTO.getDoctor().getId());
			absenceRequest.setDoctor(doctor);
		}

		if(absenceRequestDTO.getNurse() != null) {
			Nurse nurse = nurseService.findOne(absenceRequestDTO.getNurse().getId());
			absenceRequest.setNurse(nurse);
		}
		
		absenceRequest = absenceRequestService.save(absenceRequest);
		return new ResponseEntity<>(new AbsenceRequestDTO(absenceRequest), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<AbsenceRequestDTO> updateAbsenceRequest(@RequestBody AbsenceRequestDTO absenceRequestDTO) {

		// an absence request must exist
		AbsenceRequest absenceRequest = absenceRequestService.findOne(absenceRequestDTO.getId());

		if (absenceRequest == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		absenceRequest.setStartingDate(absenceRequestDTO.getStartingDate());
		absenceRequest.setEndingDate(absenceRequestDTO.getEndingDate());

		absenceRequest = absenceRequestService.save(absenceRequest);
		return new ResponseEntity<>(new AbsenceRequestDTO(absenceRequest), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteAbsenceRequest(@PathVariable Long id) {

		AbsenceRequest absenceRequest = absenceRequestService.findOne(id);

		if (absenceRequest != null) {
			absenceRequestService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{id}/doctor")	
	public ResponseEntity<DoctorDTO> getAbsenceRequestDoctor(@PathVariable Long id){
		
		AbsenceRequest absenceRequest = absenceRequestService.findOne(id);
		
		Doctor doctor = absenceRequest.getDoctor();
		DoctorDTO doctorDTO = new DoctorDTO(doctor);
		
		return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/nurse")	
	public ResponseEntity<NurseDTO> getAbsenceRequestNurse(@PathVariable Long id){
		
		AbsenceRequest absenceRequest = absenceRequestService.findOne(id);
		
		Nurse nurse = absenceRequest.getNurse();
		NurseDTO nurseDTO = new NurseDTO(nurse);
		
		return new ResponseEntity<>(nurseDTO, HttpStatus.OK);
	}
}
