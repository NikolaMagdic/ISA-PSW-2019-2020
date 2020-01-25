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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jpa.dto.ExaminationDTO;
import jpa.modeli.Examination;
import jpa.modeli.ExaminationType;
import jpa.modeli.Patient;
import jpa.service.ExaminationService;
import jpa.service.ExaminationTypeService;
import jpa.service.PatientService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8080"})
@RequestMapping(value = "api/examinations")
public class ExaminationController {

	@Autowired
	private ExaminationService examinationService;
	@Autowired
	private PatientService patientService;

	@Autowired
	private ExaminationTypeService examinationTypeService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<ExaminationDTO>> getAllExaminations(){
		List<Examination> examinations = examinationService.findAll();
		
		//convert examinations to DTOs
		List<ExaminationDTO> examinationsDTO = new ArrayList<>();
		for (Examination e : examinations) {
			examinationsDTO.add(new ExaminationDTO(e));
		}
		
		return new ResponseEntity<>(examinationsDTO, HttpStatus.OK);
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
	public ResponseEntity<ExaminationDTO> saveExamination(@RequestBody ExaminationDTO examinationDTO) {
		
		// a new examination must have examination type defined
		if(examinationDTO.getType() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		ExaminationType examinationType = examinationTypeService.findOne(examinationDTO.getType().getId());
		
		if(examinationType == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Examination examination = new Examination();
		examination.setId(null);
		examination.setDate(examinationDTO.getDate());
		examination.setDuration(examinationDTO.getDuration());
		examination.setPrice(examinationDTO.getPrice());
		examination.setType(examinationType);
		
		examination = examinationService.save(examination);
		return new ResponseEntity<>(new ExaminationDTO(examination), HttpStatus.CREATED);
	}

	@GetMapping(value = "reserve/{id1}/{id2}")
	public ResponseEntity<ExaminationDTO> updateExamination(@PathVariable Long id1 ,@PathVariable Long id2 ) {
		System.out.println("ARE YOU HEREEEEE???");
		// an examination must exist
		System.out.println(id1);
		System.out.println(id2);
		Examination examination = examinationService.findOne(id1);
		Patient patient = patientService.findOne(id2);
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
	
	
}
