package jpa.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import jpa.model.MedicalRoom;

public interface MedicalRoomRepository extends JpaRepository<MedicalRoom, Long>{

	MedicalRoom findOneById(Long id);
	
	MedicalRoom findOneByRoomCodeName(String roomCodeName);
	
	//Page<MedicalRoom> findAll(Pageable pageable);
	
	List<MedicalRoom> findAllByRoomCodeName(String roomCodeName);
}
