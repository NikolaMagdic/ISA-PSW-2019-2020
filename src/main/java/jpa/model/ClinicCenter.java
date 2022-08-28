package jpa.model;

import java.util.HashSet;
import java.util.Set;

public class ClinicCenter {

	public String name;
	private Set<Clinic> clinics =new HashSet<Clinic>();
	
}
