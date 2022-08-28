package jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

}
