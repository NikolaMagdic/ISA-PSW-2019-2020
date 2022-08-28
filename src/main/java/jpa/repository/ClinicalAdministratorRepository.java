package jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.model.ClinicalAdministrator;

public interface ClinicalAdministratorRepository extends JpaRepository<ClinicalAdministrator, Long> {

	// da li uopste treba da stavljam neke funkcije jer mi je sva logika na drugom mestu
	ClinicalAdministrator findOneByEmail(String email);
}
