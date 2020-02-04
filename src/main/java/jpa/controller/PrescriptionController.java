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

import jpa.dto.PatientDTO;
import jpa.dto.PrescriptionDTO;
import jpa.modeli.Examination;
import jpa.modeli.ExaminationReport;
import jpa.modeli.ExaminationType;
import jpa.modeli.Patient;
import jpa.modeli.Prescription;
import jpa.service.PrescriptionService;


@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8080" })
@RequestMapping(value = "api/prescriptions")
public class PrescriptionController {

	@Autowired
	private PrescriptionService service;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<PrescriptionDTO>> getAllDoctors(){
		List<Prescription> prescriptions = service.findAll();
		
		
		List<PrescriptionDTO> prescriptionsDTO = new ArrayList<>();
		for (Prescription n : prescriptions) {
			System.out.println("Naziv leka: " + n.getName());
			prescriptionsDTO.add(new PrescriptionDTO(n));
		}
		// dobro iscitava i ispisuje sve lekove iz prescriptionsDTO
		return new ResponseEntity<>(prescriptionsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<PrescriptionDTO> getDoctor(@PathVariable Long id) {

		Prescription p = service.findOne(id);

		// doctor must exist
		if (p == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new PrescriptionDTO(p), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {

		Prescription nurse = service.findOne(id);

		if (nurse != null) {
			service.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/validate/{id}")
	public ResponseEntity<Void> validatePrescription(@PathVariable Long id){
	
		Prescription p = service.findOne(id);
		
		if(p!=null){
			System.out.println("da li si ovde");
			p.setValidated(true);
			p = service.save(p);
			System.out.println("Stanje sada: "+ p.isValidated());
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
	}
	
	@GetMapping(value = "/nonAccepted")
	public ResponseEntity<List<PrescriptionDTO>> getNonAcceptedPatients() {

		List<Prescription> prescriptions = service.findAll();

		// convert Patients to DTOs
		List<PrescriptionDTO> prescriptionDTO = new ArrayList<>();
		for (Prescription p : prescriptions) {
			if(p.isValidated() == false)
				prescriptionDTO.add(new PrescriptionDTO(p));
		}

		return new ResponseEntity<>(prescriptionDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<PrescriptionDTO> savePrescription(@RequestBody PrescriptionDTO prescriptionDTO) {

		Prescription prsc = new Prescription();
		
		prsc.setId(333l);
		prsc.setName(prescriptionDTO.getName());
		prsc.setValidated(false);
		
		
		prsc = service.save(prsc);
		return new ResponseEntity<>(new PrescriptionDTO(prsc), HttpStatus.CREATED);
	}
	
	
	@GetMapping(value = "/getPrescriptionByName/{name}")
	public ResponseEntity<PrescriptionDTO> getByName(@PathVariable String name) {
		
		System.out.println("Naziv recepta koji nam je potreban: " + name);
		Prescription p = null;
		List<Prescription> prescriptions = service.findAll();
		
		for(Prescription prs : prescriptions) {
			if(prs.getName().equals(name)) {
				p = prs;
				System.out.println("ID pronadjenog recepta: " + p.getId());
			}
		}
		
		// doctor must exist
		if (p == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(new PrescriptionDTO(p), HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
