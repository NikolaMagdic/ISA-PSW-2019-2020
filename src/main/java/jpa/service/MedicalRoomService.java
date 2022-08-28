package jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jpa.model.MedicalRoom;
import jpa.repository.MedicalRoomRepository;

@Service
public class MedicalRoomService {

	@Autowired
	private MedicalRoomRepository medicalRoomRepository;
	
	public MedicalRoom findOne(Long id) {
		return medicalRoomRepository.findById(id).orElseGet(null);
	}
	
	public MedicalRoom findOneByRoomCodeName(String roomCodeName) {
		return medicalRoomRepository.findOneByRoomCodeName(roomCodeName);
	}
	
	public List<MedicalRoom> findAll(){
		return medicalRoomRepository.findAll();
	}
	
	public Page<MedicalRoom> findAll(Pageable page) {
		return medicalRoomRepository.findAll(page);
	}
	
	public MedicalRoom save(MedicalRoom medicalRoom) {
		return medicalRoomRepository.save(medicalRoom);
	}
	
	public void remove(Long id) {
		medicalRoomRepository.deleteById(id);
	}
	
}
