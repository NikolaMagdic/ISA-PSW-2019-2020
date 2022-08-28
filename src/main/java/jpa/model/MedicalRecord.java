package jpa.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MedicalRecord {
	private long id;
	
	private Set<Diagnosis>diagnosis=new HashSet<Diagnosis>();
}
