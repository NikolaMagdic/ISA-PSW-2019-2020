package jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jpa.model.Nurse;
import jpa.model.Prescription;
import jpa.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

	@Autowired
	private PrescriptionRepository repository;
	
	public Prescription findOne(Long id) {
		return repository.findById(id).orElseGet(null);
	}
	
	public List<Prescription> findAll() {
		return repository.findAll();
	}
	
	public Prescription save(Prescription p) {
		return repository.save(p);
	}

	public void remove(Long id) {
		repository.deleteById(id);
	}
}
