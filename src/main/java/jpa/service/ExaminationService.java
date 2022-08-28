package jpa.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jpa.model.Examination;
import jpa.repository.ExaminationRepository;

@Service
public class ExaminationService {

	@Autowired
	private ExaminationRepository examinationRepository;

	public Examination findOne(Long id) {
		return examinationRepository.findOneById(id);
	}
	
	public Examination findOneByDate(Date date) {
		return examinationRepository.findOneByDate(date);
	}
	
	public List<Examination> findAll(){
		return examinationRepository.findAll();
	}
	
	public Page<Examination> findAll(Pageable page){
		return examinationRepository.findAll(page);
	}
	
	public List<Examination> findByDate(Date date){
		return examinationRepository.findAllByDate(date);
	}
	
	public Examination save(Examination examination) {
		return examinationRepository.save(examination);
	}
	
	public void remove(Long id) {
		examinationRepository.deleteById(id);
	}

}
