package jpa.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jpa.model.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Long>{

	Clinic findOneByName(String name);
	
	Page<Clinic> findAll(Pageable pageable);
	
	Clinic findOneByAdress(String adress);
}
