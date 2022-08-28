package jpa.model;

import java.util.HashSet;
import java.util.Set;

public class BusinessReport {
	private long id;
	private Set<Doctor>doktori=new HashSet<Doctor>();
	private double clinicRating;
	private Set<Examination>examinations=new HashSet<Examination>();
	

}
