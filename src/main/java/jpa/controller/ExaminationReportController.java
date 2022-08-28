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

import jpa.dto.DoctorDTO;
import jpa.dto.ExaminationDTO;
import jpa.dto.ExaminationReportDTO;
import jpa.model.Diagnosis;
import jpa.model.Doctor;
import jpa.model.ExaminationReport;
import jpa.model.Patient;
import jpa.model.Prescription;
import jpa.service.DiagnosisService;
import jpa.service.ExaminationReportService;
import jpa.service.ExaminationService;
import jpa.service.PatientService;
import jpa.service.PrescriptionService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8080"},allowCredentials= "true")
@RequestMapping(value = "api/examinationReports")
public class ExaminationReportController {

	@Autowired
	private ExaminationReportService examinationReportService;
	
	@Autowired
	private PrescriptionService prescriptionService;
	
	@Autowired
	private DiagnosisService diagnosisService ;
	
	@Autowired
	private PatientService patientService ;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<ExaminationReportDTO>> getAllDiagnosis() {

		List<ExaminationReport> reports = examinationReportService.findAll();

		// convert Diagnosis to DTOs
		List<ExaminationReportDTO> reportsDTO = new ArrayList<>();
		for (ExaminationReport p : reports) {
			reportsDTO.add(new ExaminationReportDTO(p));
		}

		return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<ExaminationReportDTO> saveExaminationReport(@RequestBody ExaminationReportDTO examinationReportDTO) {

		ExaminationReport report = new ExaminationReport();
		
		// ubacujem novi uneti prescription
		if(examinationReportDTO.getPrescription() != null) {
			if(examinationReportDTO.getPrescription().getId() == 156) {
				Prescription pom = new Prescription(examinationReportDTO.getPrescription().getName(), false);
				
				prescriptionService.save(pom);
			}
		}
		
		// uzimam id novog prescriptiona iz baze
		if(examinationReportDTO.getPrescription() != null ) {
		
			Prescription p = null;
			List<Prescription> prescriptions = prescriptionService.findAll();
			
			for(Prescription prs : prescriptions) {
				if(prs.getName().equals(examinationReportDTO.getPrescription().getName())) {
					p = prs;
					System.out.println("ID pronadjenog recepta: " + p.getId());
				}
			}
			
			if (p != null) {
				report.setPrescription(p);
			}
		}
		
		if(examinationReportDTO.getDiagnosis() != null) {
			Diagnosis diagnosis = diagnosisService.findOne(examinationReportDTO.getDiagnosis().getId());
			if(diagnosis != null) {
				report.setDiagnosis(diagnosis);
			}
		}
		
		if(examinationReportDTO.getPatient() != null) {
			Patient patient = patientService.findOne(examinationReportDTO.getPatient().getId());
			
			if(patient != null) {
				report.setPatient(patient);
			}
		}
		report.setTextReport(examinationReportDTO.getTextReport());
		
		report = examinationReportService.save(report);
		return new ResponseEntity<>(new ExaminationReportDTO(report), HttpStatus.CREATED);
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ExaminationReportDTO> getDiagnosis(@PathVariable Long id) {

		ExaminationReport report = examinationReportService.findOne(id);

		
		if (report == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ExaminationReportDTO(report), HttpStatus.OK);
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<ExaminationReportDTO> updateExaminationReport(@RequestBody ExaminationReportDTO examinationDTO){
		ExaminationReport exRep = examinationReportService.findOne(examinationDTO.getId());
		
		if(exRep == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		exRep.setTextReport(examinationDTO.getTextReport());
		
		exRep = examinationReportService.save(exRep);
	
		return new ResponseEntity<>(new ExaminationReportDTO(exRep), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteExaminationReport(@PathVariable Long id) {

		ExaminationReport exRep = examinationReportService.findOne(id);

		if (exRep != null) {
			examinationReportService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
