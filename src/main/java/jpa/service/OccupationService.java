package jpa.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jpa.model.Occupation;
import jpa.repository.OccupationRepository;

@Service
public class OccupationService {


	@Autowired
	private OccupationRepository repository;
	
	public Occupation findOne(Long id) {
		return repository.findById(id).orElseGet(null);
	}
	
	public List<Occupation>findAll(){
		return repository.findAll();
	}
	
	public void remove(Long id) {
		repository.deleteById(id);
	}
	
	public Occupation save(Occupation oc) {
		return repository.save(oc);
	}
	
}
