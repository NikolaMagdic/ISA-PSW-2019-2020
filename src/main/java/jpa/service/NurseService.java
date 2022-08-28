package jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jpa.model.Doctor;
import jpa.model.Nurse;
import jpa.repository.NurseRepository;

@Service
public class NurseService {

	@Autowired
	private NurseRepository nurseRepository;
	
	public Nurse findOne(Long id) {
		return nurseRepository.findById(id).orElseGet(null);
	}
	
	public List<Nurse> findAll() {
		return nurseRepository.findAll();
	}
	
	public Nurse save(Nurse nurse) {
		return nurseRepository.save(nurse);
	}

	public void remove(Long id) {
		nurseRepository.deleteById(id);
	}
	
	public Nurse findOneByEmail(String email) {
		return nurseRepository.findOneByEmail(email);
	}
}
