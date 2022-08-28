package jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jpa.model.ExaminationType;

public interface ExaminationTypeRepository extends JpaRepository<ExaminationType, Long>{

	ExaminationType findOneById(Long id);
	
	ExaminationType findOneByTypeName(String typeName);
	
	Page<ExaminationType> findAll(Pageable pageable);
	
}
