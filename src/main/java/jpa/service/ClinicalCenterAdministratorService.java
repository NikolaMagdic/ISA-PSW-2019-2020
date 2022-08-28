package jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jpa.model.ClinicalCenterAdministrator;
import jpa.repository.ClinicalCenterAdministratorRepository;

@Service
public class ClinicalCenterAdministratorService {

	@Autowired
	private ClinicalCenterAdministratorRepository repository;
	
	public ClinicalCenterAdministrator findOne(Long id) {
		return repository.findById(id).orElseGet(null);
	}
	
	public List<ClinicalCenterAdministrator> findAll() {
		return repository.findAll();
	}
	
	public Page<ClinicalCenterAdministrator> findAll(Pageable page) {
		return repository.findAll(page);
	}
	
	public ClinicalCenterAdministrator save(ClinicalCenterAdministrator clinic) {
		return repository.save(clinic);
	}
	
	public void remove(Long id) {
		repository.deleteById(id);
	}
	
	
	public ClinicalCenterAdministrator findOneByName(String name){
		return repository.findOneByName(name);
	}
	
	public ClinicalCenterAdministrator findOneByEmail(String email) {
		return repository.findOneByEmail(email);
	}
}
