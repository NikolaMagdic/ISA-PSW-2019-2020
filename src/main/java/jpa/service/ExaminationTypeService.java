package jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jpa.model.ExaminationType;
import jpa.repository.ExaminationTypeRepository;

@Service
public class ExaminationTypeService {

	@Autowired
	private ExaminationTypeRepository examinationTypeRepository;
	
	public ExaminationType findOne(Long id) {
		return examinationTypeRepository.findById(id).orElseGet(null);
	}
	
	public ExaminationType findOneByName(String name) {
		return examinationTypeRepository.findOneByTypeName(name);
	}
	
	public List<ExaminationType> findAll(){
		return examinationTypeRepository.findAll();
	}
	
	public Page<ExaminationType> findAll(Pageable page){
		return examinationTypeRepository.findAll(page);
	}
	
	public ExaminationType save(ExaminationType examinationType) {
		return examinationTypeRepository.save(examinationType);
	}
	
	public void remove(Long id) {
		examinationTypeRepository.deleteById(id);
	}
	
}
