package jpa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import jpa.model.Occupation;

public interface OccupationRepository extends JpaRepository<Occupation, Long> {

}
