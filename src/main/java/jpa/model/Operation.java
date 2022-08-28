package jpa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Operation {
	private long id;
	private Date date;
	private ExaminationType type; //Pitaj asistenta da li moze ovako, zbog baze. Odavde vuce cenu
	private int duration;
	private MedicalRoom room;
	private Set<Doctor>doktori=new HashSet<Doctor>();
}
