package jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jpa.model.ClinicalAdministrator;
import jpa.repository.ClinicalAdministratorRepository;

@Service
public class ClinicalAdministratorService {

	@Autowired
	private ClinicalAdministratorRepository repository;
	
	public ClinicalAdministrator findOne(Long id) {
		return repository.findById(id).orElseGet(null);
	}
	
	public List<ClinicalAdministrator> findAll() {
		return repository.findAll();
	}
	
	public Page<ClinicalAdministrator> findAll(Pageable page) {
		return repository.findAll(page);
	}
	
	public ClinicalAdministrator save(ClinicalAdministrator clinic) {
		return repository.save(clinic);
	}
	
	public void remove(Long id) {
		repository.deleteById(id);
	}
	
	public ClinicalAdministrator findOneByEmail(String email) {
		return repository.findOneByEmail(email);
	}
	
}
