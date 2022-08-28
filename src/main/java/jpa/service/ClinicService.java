package jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jpa.model.Clinic;
import jpa.repository.ClinicRepository;

@Service
public class ClinicService {

	@Autowired
	private ClinicRepository clinicRepository;
	
	public Clinic findOne(Long id) {
		return clinicRepository.findById(id).orElseGet(null);
	}
	
	public List<Clinic> findAll() {
		return clinicRepository.findAll();
	}
	
	public Page<Clinic> findAll(Pageable page) {
		return clinicRepository.findAll(page);
	}
	
	public Clinic save(Clinic clinic) {
		return clinicRepository.save(clinic);
	}
	
	public void remove(Long id) {
		clinicRepository.deleteById(id);
	}
	
	public Clinic findOneByAdress(String adress){
		return clinicRepository.findOneByAdress(adress);
	}
	
	public Clinic findOneByName(String name){
		return clinicRepository.findOneByName(name);
	}
}
