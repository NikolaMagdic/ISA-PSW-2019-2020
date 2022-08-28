package jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jpa.model.AbsenceRequest;
import jpa.repository.AbsenceRequestRepository;

@Service
public class AbsenceRequestService {

	@Autowired
	private AbsenceRequestRepository absenceRequestRepository;
	
	public AbsenceRequest findOne(Long id) {
		return absenceRequestRepository.findById(id).orElseGet(null);
	}
	
	public List<AbsenceRequest> findAll(){
		return absenceRequestRepository.findAll();
	}
	
	public Page<AbsenceRequest> findAll(Pageable page){
		return absenceRequestRepository.findAll(page);
	}
	
	public AbsenceRequest save(AbsenceRequest absenceRequest) {
		return absenceRequestRepository.save(absenceRequest);
	}
	
	public void remove(Long id) {
		absenceRequestRepository.deleteById(id);
	}
}
