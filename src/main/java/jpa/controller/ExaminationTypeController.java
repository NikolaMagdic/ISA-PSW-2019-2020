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

import jpa.dto.ExaminationTypeDTO;
import jpa.modeli.ExaminationType;
import jpa.service.ExaminationTypeService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8080" })
@RequestMapping(value = "api/examinationtypes")
public class ExaminationTypeController {
	
	@Autowired
	private ExaminationTypeService examinationTypeService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<ExaminationTypeDTO>>getAllExaminationTypes(){
		List<ExaminationType> examinationTypes = examinationTypeService.findAll();
		
		//convert examination type to DTO
		List<ExaminationTypeDTO> examinationTypesDTO = new ArrayList<>();
			for (ExaminationType et : examinationTypes) {
				examinationTypesDTO.add(new ExaminationTypeDTO(et));
			}
		return new ResponseEntity<>(examinationTypesDTO, HttpStatus.OK);
	}
	

	@GetMapping(value = "/{id}")
	public ResponseEntity<ExaminationTypeDTO> getExaminationType(@PathVariable Long id) {

		ExaminationType examinationType = examinationTypeService.findOne(id);

		// examination type must exist
		if (examinationType == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ExaminationTypeDTO(examinationType), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<ExaminationTypeDTO> saveExaminationType(@RequestBody ExaminationTypeDTO examinationTypeDTO) {

		ExaminationType examinationType = new ExaminationType();
		examinationType.setId(0);
		examinationType.setTypeName(examinationTypeDTO.getTypeName());
		
		examinationType = examinationTypeService.save(examinationType);
		return new ResponseEntity<>(new ExaminationTypeDTO(examinationType), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<ExaminationTypeDTO> updateExaminationType(@RequestBody ExaminationTypeDTO examinationTypeDTO) {

		// a examination type must exist
		ExaminationType examinationType = examinationTypeService.findOne(examinationTypeDTO.getId());

		if (examinationType == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		examinationType.setTypeName(examinationTypeDTO.getTypeName());

		examinationType = examinationTypeService.save(examinationType);
		return new ResponseEntity<>(new ExaminationTypeDTO(examinationType), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteExaminationType(@PathVariable Long id) {

		ExaminationType examinationType = examinationTypeService.findOne(id);

		if (examinationType != null) {
			examinationTypeService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
