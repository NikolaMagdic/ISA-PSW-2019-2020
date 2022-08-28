package jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jpa.model.Examination;
import jpa.model.ExaminationReport;


public interface ExaminationReportRepository extends JpaRepository<ExaminationReport, Long>{

	ExaminationReport findOneById(Long id);
	
	Page<ExaminationReport> findAll(Pageable pageable);
	
}
