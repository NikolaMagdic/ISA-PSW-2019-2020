package jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jpa.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

	Doctor findOneById(Long id);
	
	Doctor findOneByName(String name);
	
	Doctor findOneBySurname(String surname);
	
	Doctor findOneByEmail(String email);
	
	Page<Doctor> findAll(Pageable pageable);
	
	List<Doctor> findAllByName(String name);
	
	List<Doctor> findAllBySurname(String surname);
	
	List<Doctor> findByNameAndSurnameAllIgnoringCase(String name, String surname);
}
