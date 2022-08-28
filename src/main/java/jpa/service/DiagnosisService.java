package jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jpa.model.Diagnosis;
import jpa.repository.DiagnosisRepository;



@Service
public class DiagnosisService {
	@Autowired
	private DiagnosisRepository diagnosisRepository;
	
	public Diagnosis findOne(Long id) {
		return diagnosisRepository.findById(id).orElseGet(null);
	}

	public List<Diagnosis> findAll() {
		return diagnosisRepository.findAll();
	}
	
	public Page<Diagnosis> findAll(Pageable page) {
		return diagnosisRepository.findAll(page);
	}

	public Diagnosis save(Diagnosis student) {
		return diagnosisRepository.save(student);
	}

	public void remove(Long id) {
		diagnosisRepository.deleteById(id);
	}
	
	public Diagnosis findByIndex(String index) {
		return diagnosisRepository.findOneByName(index);
	}
	
	public List<Diagnosis> findByLastName(String name) {
		return diagnosisRepository.findAllByName(name);
	}
	
	public List<Diagnosis> findByName(String name) {
		return diagnosisRepository.findByNameAllIgnoringCase(name);
	}
	
	public List<Diagnosis> pronadjiPoPrezimenu(String ime) {
		return diagnosisRepository.pronadjiDijagnozePoImenu(ime);
	}
}
