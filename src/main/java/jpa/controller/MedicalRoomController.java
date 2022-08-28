
package jpa.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jpa.dto.MedicalRoomDTO;
import jpa.model.Clinic;
import jpa.model.Examination;
import jpa.model.MedicalRoom;
import jpa.model.Occupation;
import jpa.service.ClinicService;
import jpa.service.ExaminationService;
import jpa.service.MedicalRoomService;
import jpa.service.OccupationService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8080" },allowCredentials= "true")
@RequestMapping(value = "api/medicalrooms")
public class MedicalRoomController {

	@Autowired
	private MedicalRoomService medicalRoomService;
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private OccupationService occupationService;
	
	@Autowired
	private ExaminationService examinationService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<MedicalRoomDTO>>getAllMedicalRooms(){
		List<MedicalRoom> medicalRooms = medicalRoomService.findAll();
		
		//convert medical rooms to DTO
		List<MedicalRoomDTO> medicalRoomsDTO = new ArrayList<>();
			for (MedicalRoom mr : medicalRooms) {
				medicalRoomsDTO.add(new MedicalRoomDTO(mr));
			}
		return new ResponseEntity<>(medicalRoomsDTO, HttpStatus.OK);
	}
	
	// prima id od examinationa za koji bi trebalo da se vezu sale
	@GetMapping(value = "/freeOperationRoomForExamination/{idExamination}")
	public ResponseEntity<List<MedicalRoomDTO>>getFreeOperationRooms(@PathVariable Long idExamination){
		
		Examination examination = examinationService.findOne(idExamination);
		
		List<MedicalRoom> medicalRooms = medicalRoomService.findAll();
		
		//convert medical rooms to DTO
		List<MedicalRoomDTO> medicalRoomsDTO = new ArrayList<>();
		
		for(MedicalRoom room : medicalRooms) {
			boolean slobodna = true;
			// IZMENIIII da bude samo za operacione sale
			//if(room.getOperational()) {
				Set<Occupation> occupations = room.getOccupations();
				for(Occupation oc : occupations) { 
					
					// mora da se mnozi sa 60 jer examination pamti samo sate
					if( !(!oc.getDate().equals(examination.getDate()) || examination.getEndTime() <= oc.getPocetniTrenutak() ||  examination.getStartTime() >= oc.getKrajnjiTrenutak())) { // 
						
						System.out.println("Skontao je da je isto");
						slobodna = false;
					} 
				}
			//}
			
			if(slobodna) {
				medicalRoomsDTO.add(new MedicalRoomDTO(room));
			}
		}
			
		return new ResponseEntity<>(medicalRoomsDTO, HttpStatus.OK);
	}
	

	@GetMapping(value = "/freeOperationRoomForOccupation/{date}/{startingSum}/{endingSum}")
	public ResponseEntity<List<MedicalRoomDTO>>getFreeOperationRoomsForOccupation(@PathVariable Date date,@PathVariable Integer startingSum, @PathVariable Integer endingSum){
		
		//Examination examination = examinationService.findOne(idExamination);
		
		List<MedicalRoom> medicalRooms = medicalRoomService.findAll();
		
		//convert medical rooms to DTO
		List<MedicalRoomDTO> medicalRoomsDTO = new ArrayList<>();
		
		for(MedicalRoom room : medicalRooms) {
			boolean slobodna = true;
			// IZMENIIII da bude samo za operacione sale
			//if(room.getOperational()) {
				Set<Occupation> occupations = room.getOccupations();
				for(Occupation oc : occupations) { 
					
					if( !(!oc.getDate().equals(date) || endingSum <= oc.getPocetniTrenutak() ||  startingSum >= oc.getKrajnjiTrenutak())) { // 
						
						System.out.println("Skontao je da je isto");
						slobodna = false;
					} 
				}
			//}
			
			if(slobodna) {
				medicalRoomsDTO.add(new MedicalRoomDTO(room));
			}
		}
			
		return new ResponseEntity<>(medicalRoomsDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/bookOperationRoom/{id}", consumes = "application/json")
	public void bookOperationRoom(@PathVariable Long id, @RequestBody Occupation occupationDTO){
		
		MedicalRoom operationRoom = medicalRoomService.findOne(id);
		
		Occupation oc = new Occupation(occupationDTO.getDate(), occupationDTO.getPocetniTrenutak(), occupationDTO.getKrajnjiTrenutak());

		oc.setMedicalRoom(operationRoom);
		oc = occupationService.save(oc);
				
			
		return ;
	}
	

	

	@GetMapping(value = "/{id}")
	public ResponseEntity<MedicalRoomDTO> getMedicalRoom(@PathVariable Long id) {

		MedicalRoom medicalRoom = medicalRoomService.findOne(id);

		// room must exist
		if (medicalRoom == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new MedicalRoomDTO(medicalRoom), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<MedicalRoomDTO> saveMedicalRoom(@RequestBody MedicalRoomDTO medicalRoomDTO) {

		// new room must have clinic defined
		if(medicalRoomDTO.getClinic() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Clinic clinic = clinicService.findOne(medicalRoomDTO.getClinic().getId());
		
		if(clinic == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		MedicalRoom medicalRoom = new MedicalRoom();
		medicalRoom.setId(0);
		medicalRoom.setOperational(medicalRoomDTO.getOperational());
		medicalRoom.setReserved(medicalRoomDTO.getReserved());
		medicalRoom.setRoomCodeName(medicalRoomDTO.getRoomCodeName());
		medicalRoom.setRoomNumber(medicalRoomDTO.getRoomNumber());
		medicalRoom.setDate(medicalRoomDTO.getDate());
		medicalRoom.setClinic(clinic);
		
		medicalRoom = medicalRoomService.save(medicalRoom);
		return new ResponseEntity<>(new MedicalRoomDTO(medicalRoom), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<MedicalRoomDTO> updateMedicalRoom(@RequestBody MedicalRoomDTO medicalRoomDTO) {

		// a room must exist
		MedicalRoom medicalRoom = medicalRoomService.findOne(medicalRoomDTO.getId());

		if (medicalRoom == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		medicalRoom.setOperational(medicalRoomDTO.getOperational());
		medicalRoom.setReserved(medicalRoomDTO.getReserved());
		medicalRoom.setRoomCodeName(medicalRoomDTO.getRoomCodeName());
		medicalRoom.setRoomNumber(medicalRoomDTO.getRoomNumber());
		medicalRoom.setDate(medicalRoomDTO.getDate());
		
		medicalRoom = medicalRoomService.save(medicalRoom);
		return new ResponseEntity<>(new MedicalRoomDTO(medicalRoom), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteMedicalRoom(@PathVariable Long id) {

		MedicalRoom medicalRoom = medicalRoomService.findOne(id);

		if (medicalRoom != null) {
			if(!medicalRoom.getReserved()) 
				medicalRoomService.remove(id);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	
	// Booking rooms for examinations
	
	@GetMapping(value = "/freeExaminationRoomForExamination/{idExamination}")
	public ResponseEntity<List<MedicalRoomDTO>>getFreeExaminationRooms(@PathVariable Long idExamination){
		
		Examination examination = examinationService.findOne(idExamination);
		
		List<MedicalRoom> medicalRooms = medicalRoomService.findAll();
		
		//convert medical rooms to DTO
		List<MedicalRoomDTO> medicalRoomsDTO = new ArrayList<>();
		
		for(MedicalRoom room : medicalRooms) {
			boolean free = true;
			// We look only for rooms that are not operational
			if(!room.getOperational()) {
				Set<Occupation> occupations = room.getOccupations();
				for(Occupation oc : occupations) { 
					
					if( !(!oc.getDate().equals(examination.getDate()) || examination.getEndTime() <= oc.getPocetniTrenutak() ||  examination.getStartTime() >= oc.getKrajnjiTrenutak())) { 
						
						free = false;
					} 
				}
				if(free) {
					medicalRoomsDTO.add(new MedicalRoomDTO(room));
				}
			}
			
		}
			
		return new ResponseEntity<>(medicalRoomsDTO, HttpStatus.OK);
	}
	

	@GetMapping(value = "/freeExaminationRoomForOccupation/{date}/{startingSum}/{endingSum}")
	public ResponseEntity<List<MedicalRoomDTO>>getFreeExaminationRoomsForOccupation(@PathVariable Date date, @PathVariable Integer startingSum, @PathVariable Integer endingSum){
		
		List<MedicalRoom> medicalRooms = medicalRoomService.findAll();
		
		//convert medical rooms to DTO
		List<MedicalRoomDTO> medicalRoomsDTO = new ArrayList<>();
		
		for(MedicalRoom room : medicalRooms) {
			boolean free = true;
			// Only if room is for ordinary examination consider it
			if(!room.getOperational()) {
				Set<Occupation> occupations = room.getOccupations();
				for(Occupation oc : occupations) { 
					
					if( !(!oc.getDate().equals(date) || endingSum <= oc.getPocetniTrenutak() ||  startingSum >= oc.getKrajnjiTrenutak())) {  
						
						free = false;
					} 
				}
				
				if(free) {
					medicalRoomsDTO.add(new MedicalRoomDTO(room));
				}
			}
			
		}
			
		return new ResponseEntity<>(medicalRoomsDTO, HttpStatus.OK);
	}
	
}
