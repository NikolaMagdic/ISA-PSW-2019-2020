package jpa.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jpa.dto.ClinicDTO;
import jpa.dto.DoctorDTO;
import jpa.dto.MedicalRoomDTO;
import jpa.model.Clinic;
import jpa.model.Doctor;
import jpa.model.Examination;
import jpa.model.MedicalRoom;
import jpa.model.Patient;
import jpa.security.TokenUtils;
import jpa.service.ClinicService;
import jpa.service.ExaminationService;
import jpa.service.PatientService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8080" }, allowCredentials = "true")
@RequestMapping(value = "api/clinics")
public class ClinicController {

	@Autowired
	private ClinicService clinicService;
	@Autowired
	private ExaminationService examinationService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private TokenUtils tokenUtils;
	
	@PreAuthorize("hasAnyRole('PATIENT', 'CLINICAL_CENTER_ADMIN')")
	@GetMapping(value = "/all")
	public ResponseEntity<List<ClinicDTO>> getAllClinics() {
		 
		List<Clinic> clinics = clinicService.findAll();

		// convert clinics to DTOs
		List<ClinicDTO> clinicDTO = new ArrayList<>();
		
		for (Clinic c : clinics) {
			clinicDTO.add(new ClinicDTO(c));
		}
		
		return new ResponseEntity<>(clinicDTO, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('PATIENT')") //izmeni da mogu da doprem i kada sam administrator
	@GetMapping(value = "/allClinicsOfPatient")
	public ResponseEntity<List<ClinicDTO>> getAllClinicsPatientHasBeen(HttpServletRequest request) {
		 
		String jwtToken = this.tokenUtils.getToken(request);
		String username = tokenUtils.getUsernameFromToken(jwtToken);
		
		List<Examination> examinations = examinationService.findAll();
		Patient p = patientService.findOneByEmail(username);
		// convert clinics to DTOs
		List<ClinicDTO> clinicDTO = new ArrayList<>();
		
		for (Examination e : examinations) {
			if(e.getPatient()!=null){
			if(e.getPatient().getId()==(p.getId())){
				boolean vecPostoji = false;
				for(ClinicDTO cDTO : clinicDTO){
					if(cDTO.getId() == e.getClinic().getId()){
						vecPostoji = true;
					}	}
					for(Clinic cc : p.getClinics()){
						if(e.getClinic().getId() == cc.getId()){
							vecPostoji = true;
						}
					}
				
				if(vecPostoji == false){
					clinicDTO.add(new ClinicDTO(e.getClinic()));
					p.getClinics().add(e.getClinic());
				}
			}}
		}
	
		return new ResponseEntity<>(clinicDTO, HttpStatus.OK);

	}
	
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/allFiltered/{date}/{type}")
	public ResponseEntity<List<ClinicDTO>> getAllFilteredClinics(@PathVariable Date date, @PathVariable String type) {
		
		List<Clinic> clinics = clinicService.findAll();

		// convert clinics to DTOs
		List<ClinicDTO> clinicDTO = new ArrayList<>();
		System.out.println(date);
		System.out.println(type);
		//System.out.println((Date) Session.getAttribute("filterDate"));
		for (Clinic c : clinics) {
			boolean nasao_kliniku=false;
			boolean slobodan_doktor=true;
			boolean usao=false;
			for(Doctor d : c.getDoctors()){
			
				for(Examination e : d.getExaminations()){
					usao=true;
					if(e.getAccepted()==true){
					System.out.println("ULAZIS OVDE??");
					if(e.getDate().compareTo(date)==0 ){
						if(e.getPatient()!=null){
						slobodan_doktor=false;}
						if(!type.equals("")){
						if(!e.getType().getTypeName().toLowerCase().contains(type.toLowerCase())){
							slobodan_doktor=false;}}
					}}}
				if(slobodan_doktor==true && nasao_kliniku==false && usao==true){
					clinicDTO.add(new ClinicDTO(c));
					nasao_kliniku=true;
				}
			}
			
			
		}
		
		return new ResponseEntity<>(clinicDTO, HttpStatus.OK);
		
	}
/*	@GetMapping(value = "/filter/{date}/{type}")
	public ResponseEntity<List<ClinicDTO>> getFiltered(@PathVariable Date date,@PathVariable String type,HttpSession Session) {
		System.out.println(Session.getAttribute("role"));
		if(Session.getAttribute("role").equals("PATIENT")){
		Session.setAttribute("filterDate", date);
		Session.setAttribute("filterType", type);
		
		return new ResponseEntity<>(null, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}*/
	
	@GetMapping(value = "select/{date}/{type}/{id1}")
	public ResponseEntity<List<DoctorDTO>> updateExamination(@PathVariable Date date,@PathVariable String type,@PathVariable Long id1,HttpSession Session ) {
	
	
		System.out.println(id1);
		
		Clinic clinic = clinicService.findOne(id1);
		
		if (clinic == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<DoctorDTO> doctorDTO = new ArrayList<>();
		
		
				for(Doctor d : clinic.getDoctors()){
					boolean slobodan_doktor=true;
					boolean usao = false;
				
					for(Examination e : d.getExaminations()){
						usao=true;
						if(e.getDate().compareTo(date)==0 ){
							if(e.getAccepted()==true){
							if(e.getPatient()!=null){
							slobodan_doktor=false;}
							if(!type.equals("")){
							if(!e.getType().getTypeName().toLowerCase().contains(type.toLowerCase())){
								slobodan_doktor=false;}}
						}}}
					if(slobodan_doktor==true && usao==true){
						doctorDTO.add(new DoctorDTO(d));
				
					}
					}
				
				
			
		return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
	}
	
	
	
	@GetMapping
	public ResponseEntity<List<ClinicDTO>> getClinicsPage(Pageable page) {

		
		Page<Clinic> clinics = clinicService.findAll(page);

		// convert clinics to DTOs
		List<ClinicDTO> clinicsDTO = new ArrayList<>();
		for (Clinic s : clinics) {
			clinicsDTO.add(new ClinicDTO(s));
		}

		return new ResponseEntity<>(clinicsDTO, HttpStatus.OK);
	}
	@GetMapping(value = "rate/{id1}/{rating}")
	public ResponseEntity<ClinicDTO> rateClicinc(@PathVariable Long id1,@PathVariable double rating,HttpSession Session) {
		System.out.println("cek jel ulazis ovde???");
		
		Clinic clinic= clinicService.findOne(id1);

		//if(clinic.getGradeSum()!=0){
			clinic.setGradeNumber(clinic.getGradeNumber() + 1);
		//}
		
		clinic.setGradeSum(clinic.getGradeSum() + rating);
		Patient patient = patientService.findOne((Long)Session.getAttribute("id"));
		patient.getClinics().add(clinic);
		clinic.getPatients().add(patient);
		System.out.println(patient.getClinics().size());
		patient= patientService.save(patient);
		System.out.println(patient.getClinics().size());
		clinic = clinicService.save(clinic);
		
		
		return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClinicDTO> getClinic(@PathVariable Long id) {

		Clinic clinic = clinicService.findOne(id);

		if (clinic == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ClinicDTO>(new ClinicDTO(clinic), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<ClinicDTO> saveClinic(@RequestBody ClinicDTO clinicDTO) {

		Clinic clinic = new Clinic();
		clinic.setId(222L);
		clinic.setName(clinicDTO.getName());
		clinic.setAdress(clinicDTO.getAdress());
		clinic.setDescription(clinicDTO.getDescription());
		clinic.setGradeSum(clinicDTO.getGradeSum());
		clinic.setGradeNumber(clinicDTO.getGradeNumber());
		clinic.setLongitude(clinicDTO.getLongitude());
		clinic.setLatitude(clinicDTO.getLatitude());
		
		System.out.println("********* Prosledjeni naziv klinike: " + clinicDTO.getName()+ " ***************");

		clinic = clinicService.save(clinic);
		return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.CREATED);
	}
	
	
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<ClinicDTO> updateClinic(@RequestBody ClinicDTO clinicDTO) {

		// a clinic must exist
		Clinic clinic = clinicService.findOne(clinicDTO.getId());

		if (clinic == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		clinic.setName(clinicDTO.getName());
		clinic.setAdress(clinicDTO.getAdress());
		clinic.setDescription(clinicDTO.getDescription());
		clinic.setGradeSum(clinicDTO.getGradeSum());
		clinic.setGradeNumber(clinicDTO.getGradeNumber());
		clinic.setLongitude(clinicDTO.getLongitude());
		clinic.setLatitude(clinicDTO.getLatitude());

		System.out.println("********* Upadateovani naziv klinike: " + clinicDTO.getName()+ " ***************");
		
		clinic = clinicService.save(clinic);
		return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {

		Clinic clinic = clinicService.findOne(id);

		if (clinic != null) {
			clinicService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping(value = "/findName")
	public ResponseEntity<ClinicDTO> getClinicByName(@RequestParam String name) {

		Clinic clinic = clinicService.findOneByName(name);
		if (clinic == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/findAdress")
	public ResponseEntity<ClinicDTO> getClinicByAdress(@RequestParam String adress) {

		Clinic clinic = clinicService.findOneByName(adress);
		if (clinic == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/{clinicId}/doctors")
	public ResponseEntity<List<DoctorDTO>> getClinicDoctors(@PathVariable Long clinicId) {
		Clinic clinic = clinicService.findOne(clinicId);
		Set<Doctor> doctors = clinic.getDoctors();
		List<DoctorDTO> doctorsDTO = new ArrayList<>();
		for (Doctor d : doctors) {
			DoctorDTO doctorDTO = new DoctorDTO();
			doctorDTO.setId(d.getId());
			doctorDTO.setName(d.getName());
			doctorDTO.setSurname(d.getSurname());
			doctorDTO.setPassword(d.getPassword());
			doctorDTO.setEmail(d.getEmail());
			doctorDTO.setAdress(d.getAdress());
			doctorDTO.setCity(d.getCity());
			doctorDTO.setState(d.getState());
			doctorDTO.setPhone(d.getPhone());
			doctorDTO.setWorkHourStart(d.getWorkHourStart());
			doctorDTO.setWorkHourFinish(d.getWorkHourFinish());

			doctorsDTO.add(doctorDTO);
		}
		return new ResponseEntity<>(doctorsDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/{clinicId}/rooms")
	public ResponseEntity<List<MedicalRoomDTO>> getRoomsOfClinic(@PathVariable Long clinicId){
		Clinic clinic = clinicService.findOne(clinicId);
		Set<MedicalRoom> medicalRooms = clinic.getRooms();
		List<MedicalRoomDTO> medicalRoomsDTO = new ArrayList<>();
		for(MedicalRoom mr : medicalRooms) {
			MedicalRoomDTO medicalRoomDTO = new MedicalRoomDTO();
			medicalRoomDTO.setId(mr.getId());
			medicalRoomDTO.setReserved(mr.getReserved());
			medicalRoomDTO.setOperational(mr.getOperational());
			medicalRoomDTO.setRoomCodeName(mr.getRoomCodeName());
			medicalRoomDTO.setRoomNumber(mr.getRoomNumber());
			medicalRoomDTO.setDate(mr.getDate());
			
			medicalRoomsDTO.add(medicalRoomDTO);
		}
		return new ResponseEntity<>(medicalRoomsDTO, HttpStatus.OK);
	} 
	
	
}
