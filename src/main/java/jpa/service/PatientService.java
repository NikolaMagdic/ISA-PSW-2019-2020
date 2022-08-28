package jpa.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jpa.model.Patient;
import jpa.repository.PatientRepository;


@Service
public class PatientService {
	@Autowired
	private PatientRepository patientRepository;
	
	public Patient findOne(Long id) {
		return patientRepository.findOneById(id);
	}

	public List<Patient> findAll() {
		return patientRepository.findAll();
	}
	
	public Page<Patient> findAll(Pageable page) {
		return patientRepository.findAll(page);
	}

	public Patient save(Patient patient) {
		return patientRepository.save(patient);
	}

	public void remove(Long id) {
		patientRepository.deleteById(id);
	}
	
	public Patient findByIndex(String index) {
		return patientRepository.findOneByName(index);
	}
	
	public List<Patient> findByLastName(String lastName) {
		return patientRepository.findAllBySurname(lastName);
	}
	
	public List<Patient> findByFirstNameAndLastName(String firstName, String lastName) {
		return patientRepository.findByNameAndSurnameAllIgnoringCase(firstName, lastName);
	}
	
	public List<Patient> pronadjiPoPrezimenu(String prezime) {
		return patientRepository.pronadjiPacijentePoPrezimenu(prezime);
	}
	
	public Patient findOneByEmail(String email) {
		return patientRepository.findOneByEmail(email);
	}
	
}
