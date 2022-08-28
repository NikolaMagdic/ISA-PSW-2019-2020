package jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jpa.model.AbsenceRequest;

public interface AbsenceRequestRepository extends JpaRepository<AbsenceRequest, Long>{

	AbsenceRequest findOneById(Long id);
	
	//For now I did not write functions for finding AbsenceRequest by Date, I will consider writing them later
	
	Page<AbsenceRequest> findAll(Pageable pageable);
	
}
