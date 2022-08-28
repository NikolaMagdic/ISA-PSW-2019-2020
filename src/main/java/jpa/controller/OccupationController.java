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

import jpa.dto.OccupationDTO;
import jpa.model.Diagnosis;
import jpa.model.Occupation;
import jpa.service.OccupationService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8080" },allowCredentials= "true")
@RequestMapping(value = "api/occupations")
public class OccupationController {

	private Logger logger = LoggerFactory.getLogger(PatientController.class);
	@Autowired
	private OccupationService occupationService;
	
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<OccupationDTO>> getAllDiagnosis() {

		List<Occupation> ocupations = occupationService.findAll();

		// convert Diagnosis to DTOs
		List<OccupationDTO> diagnosisDTO = new ArrayList<>();
		for (Occupation o : ocupations) {
			diagnosisDTO.add(new OccupationDTO(o));
		}

		return new ResponseEntity<>(diagnosisDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<OccupationDTO> saveOccupation(@RequestBody OccupationDTO oc) {

		Occupation occupation = new Occupation();
		occupation.setH7(oc.getH7());
		occupation.setH8(oc.getH8());
		occupation.setH9(oc.getH9());
		occupation.setH10(oc.getH10());
		occupation.setH11(oc.getH11());
		occupation.setH12(oc.getH12());
		occupation.setH13(oc.getH13());
		occupation.setH14(oc.getH14());
		occupation.setH15(oc.getH15());
		occupation.setH16(oc.getH16());
		occupation.setH17(oc.getH17());
		
		occupation.setDate(oc.getDate());
		

		occupation = occupationService.save(occupation);
		return new ResponseEntity<>(new OccupationDTO(occupation), HttpStatus.CREATED);
	}
	
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<OccupationDTO> updateDiagnosis(@RequestBody OccupationDTO oc) {

		// a Diagnosis must exist
		Occupation occupation = occupationService.findOne(oc.getId());

		if (occupation == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		occupation.setH7(oc.getH7());
		occupation.setH8(oc.getH8());
		occupation.setH9(oc.getH9());
		occupation.setH10(oc.getH10());
		occupation.setH11(oc.getH11());
		occupation.setH12(oc.getH12());
		occupation.setH13(oc.getH13());
		occupation.setH14(oc.getH14());
		occupation.setH15(oc.getH15());
		occupation.setH16(oc.getH16());
		occupation.setH17(oc.getH17());
		
		occupation.setDate(oc.getDate());
		

		occupation = occupationService.save(occupation);
		return new ResponseEntity<>(new OccupationDTO(occupation), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteDiagnosis(@PathVariable Long id) {

		Occupation occupation = occupationService.findOne(id);

		if (occupation != null) {
			occupationService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
