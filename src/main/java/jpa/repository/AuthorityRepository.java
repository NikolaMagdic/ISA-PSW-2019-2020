package jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{
	Authority findByName(String name);
}
