package jpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RestController;

import jpa.dto.DiagnosisDTO;
import jpa.model.Diagnosis;
import jpa.service.DiagnosisService;


import jpa.dto.DiagnosisDTO;
import jpa.service.DiagnosisService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8080" },allowCredentials= "true")
@RequestMapping(value = "api/diagnosis")
public class DiagnosisControler {
	@Autowired
	private DiagnosisService diagnosisService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<DiagnosisDTO>> getAllDiagnosis() {

		List<Diagnosis> diagnosis = diagnosisService.findAll();

		// convert Diagnosis to DTOs
		List<DiagnosisDTO> diagnosisDTO = new ArrayList<>();
		for (Diagnosis p : diagnosis) {
			diagnosisDTO.add(new DiagnosisDTO(p));
		}

		return new ResponseEntity<>(diagnosisDTO, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<DiagnosisDTO>> getDiagnosissPage(Pageable page) {

		
		Page<Diagnosis> diagnosis = diagnosisService.findAll(page);

	
		List<DiagnosisDTO> diagnosisDTO = new ArrayList<>();
		for (Diagnosis p : diagnosis) {
			diagnosisDTO.add(new DiagnosisDTO(p));
		}

		return new ResponseEntity<>(diagnosisDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<DiagnosisDTO> getDiagnosis(@PathVariable Long id) {

		Diagnosis Diagnosis = diagnosisService.findOne(id);

		
		if (Diagnosis == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new DiagnosisDTO(Diagnosis), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<DiagnosisDTO> saveDiagnosis(@RequestBody DiagnosisDTO DiagnosisDTO) {

		Diagnosis Diagnosis = new Diagnosis();
		Diagnosis.setName(DiagnosisDTO.getName());
		

		Diagnosis = diagnosisService.save(Diagnosis);
		return new ResponseEntity<>(new DiagnosisDTO(Diagnosis), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<DiagnosisDTO> updateDiagnosis(@RequestBody DiagnosisDTO DiagnosisDTO) {

		// a Diagnosis must exist
		Diagnosis Diagnosis = diagnosisService.findOne(DiagnosisDTO.getId());

		if (Diagnosis == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Diagnosis.setName(DiagnosisDTO.getName());
		

		Diagnosis = diagnosisService.save(Diagnosis);
		return new ResponseEntity<>(new DiagnosisDTO(Diagnosis), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteDiagnosis(@PathVariable Long id) {

		Diagnosis Diagnosis = diagnosisService.findOne(id);

		if (Diagnosis != null) {
			diagnosisService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/findName")
	public ResponseEntity<DiagnosisDTO> getDiagnosisByIndex(@RequestParam String index) {

		Diagnosis Diagnosis = diagnosisService.findByIndex(index);
		if (Diagnosis == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new DiagnosisDTO(Diagnosis), HttpStatus.OK);
	}

	

/*	@GetMapping(value = "/{DiagnosisId}/Prescriptions")
	public ResponseEntity<List<PrescriptionDTO>> getDiagnosisExams(@PathVariable Long DiagnosisId) {
		Diagnosis Diagnosis = diagnosisService.findOne(DiagnosisId);
		Set<Prescription> prescriptions = Diagnosis.getPrescription();
		List<PrescriptionDTO> examsDTO = new ArrayList<>();
		for (Prescription p : Prescription) {
			PrescriptionDTO prescriptionDTO = new ExamDTO();
			prescriptionDTO.setId(e.getId());
			prescriptionDTO.setGrade(e.getGrade());
			prescriptionDTO.setDate(e.getDate());
			prescriptionDTO.setCourse(new CourseDTO(e.getCourse()));
			prescriptionDTO.setDiagnosis(new DiagnosisDTO(e.getDiagnosis()));

			examsDTO.add(prescriptionDTO);
		}
		return new ResponseEntity<>(examsDTO, HttpStatus.OK);
	}*/
	// Ovo dodati kada se za sve MODELE URADEEEEE
}
