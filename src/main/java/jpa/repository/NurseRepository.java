package jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.model.Nurse;

public interface NurseRepository extends JpaRepository<Nurse, Long> {

	Nurse findOneByEmail(String email);
}
