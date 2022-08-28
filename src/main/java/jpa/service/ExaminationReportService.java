package jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jpa.model.ExaminationReport;
import jpa.repository.ExaminationReportRepository;

@Service
public class ExaminationReportService {

	@Autowired
	private ExaminationReportRepository examinationReportRepository;
	
	
	public ExaminationReport findOne(Long id) {
		return examinationReportRepository.findById(id).orElseGet(null);
	}
	
	public List<ExaminationReport> findAll(){
		return examinationReportRepository.findAll();
	}
	
	public Page<ExaminationReport> findAll(Pageable page){
		return examinationReportRepository.findAll(page);
	}
	
	public ExaminationReport save(ExaminationReport examination) {
		return examinationReportRepository.save(examination);
	}
	
	public void remove(Long id) {
		examinationReportRepository.deleteById(id);
	}
}
