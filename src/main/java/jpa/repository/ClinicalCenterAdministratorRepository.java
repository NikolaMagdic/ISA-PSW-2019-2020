package jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jpa.model.ClinicalCenterAdministrator;

public interface ClinicalCenterAdministratorRepository extends JpaRepository<ClinicalCenterAdministrator, Long> {
	
	ClinicalCenterAdministrator findOneByName(String name);
	Page<ClinicalCenterAdministrator> findAll(Pageable pageable);
	ClinicalCenterAdministrator findOneBySurname(String surname);
	
	ClinicalCenterAdministrator findOneByEmail(String email);
}
