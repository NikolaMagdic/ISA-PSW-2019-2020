package jpa.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jpa.model.Examination;

public interface ExaminationRepository extends JpaRepository<Examination, Long>{

	Examination findOneById(Long id);
	
	Examination findOneByDate(Date d);
	
	Page<Examination> findAll(Pageable pageable);
	
	List<Examination> findAllByDate(Date d);
	
}
