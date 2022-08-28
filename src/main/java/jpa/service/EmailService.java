package jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import jpa.model.ClinicalAdministrator;
import jpa.model.ClinicalCenterAdministrator;
import jpa.model.Doctor;
import jpa.model.Examination;
import jpa.model.Nurse;
import jpa.model.Patient;



@Service
@CrossOrigin( allowCredentials= "true")
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;


	@Async
	public void sendNotificaitionAsync(Patient patient) throws MailException, InterruptedException {

		
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Validacija pacijenta: " + patient.getName());
		mail.setText("Pozdrav " + patient.getName() + ",\n\n Vas zahtev za registaciju je prihacen. Ovde ces aktivirati nalog "+ "http://localhost:8082/api/patients/validate/" + patient.getId());
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}
	@Async
	public void sendNotificaitionAsyncReserve(Patient patient,Examination examination) throws MailException, InterruptedException {

		if(examination.getPatient()==null){
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Validacija pacijenta: " + patient.getName());
		mail.setText("Pozdrav " + patient.getName() + ",\n\n Vas zahtev za pregled. Ovde ce se aktivirati  "+ "http://localhost:8082/api/examinations/reserve/" + examination.getId());
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
		}
		else{
			System.out.println("vec rezervisan");
		}
	}
	
	@Async
	public void sendNotificaitionAsync(ClinicalCenterAdministrator admin) throws MailException, InterruptedException {


		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Validacija administratora: " + admin.getName());
		mail.setText("Pozdrav " + admin.getName() + ",\n\n Klikom na link ces potvrditi svoju registraciju: " + "http://localhost:8080/ValidateClinicalCenterAdministrator/"+admin.getId());
		//mail.setText("http://localhost:8082/api/clinicalCenterAdministrators/validate/"+admin.getId());
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}
	
	@Async
	public void sendNotificaitionAsync(ClinicalAdministrator admin) throws MailException, InterruptedException {


		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Validacija administratora: " + admin.getName());
		mail.setText("Pozdrav " + admin.getName() + ",\n\n Klikom na link ces potvrditi svoju registraciju: " + "http://localhost:8080/ValidateClinicalCenterAdministrator/"+admin.getId());
		//mail.setText("http://localhost:8082/api/clinicalCenterAdministrators/validate/"+admin.getId());
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}

	// send auto generated email with predefined message
	public void sendNotificaitionSync(Patient patient) throws MailException, InterruptedException {


		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Primer slanja emaila pomocu asinhronog Spring taska");
		mail.setText("Pozdrav " + patient.getName() + ",\n\nhvala što pratiš ISA.");
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}
	
	// send email with custom message
	public void sendNotificaitionSync(Patient patient, String message) throws MailException, InterruptedException {


		System.out.println("Slanje emaila. Tekst email-a: " + message);

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject(message);
		mail.setText("Pozdrav " + patient.getName() + ",\n\n." + message);
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}
	
	public void sendNotificaitionReject(Patient patient, String message) throws MailException, InterruptedException {


		System.out.println("Slanje emaila. Tekst email-a: " + message);

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Vas zahtev za registraciju je odbijen");
		mail.setText("Pozdrav " + patient.getName() + ",\n Vas zahtev za registraciju je odbijen.\nRazlog odbijanja:\n" + message);
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}
	
	//E-mails for absence requests for doctors
	@Async
	public void sendNotificationAccept(Doctor doctor) throws MailException, InterruptedException{
		
		System.out.println("Slanje emaila... ");
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Vaš zahtev za godišnji odmor je prihvaćen");
		mail.setText("Pozdrav " + doctor.getName() + ",\nVaš zahtev za godišnji odmor je prihvaćen.");
		javaMailSender.send(mail);
		
		System.out.println("Email je poslat!");
	}
	
	@Async
	public void sendNotificationDecline(Doctor doctor, String message) throws MailException, InterruptedException{
		
		System.out.println("Slanje emaila...");
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Vaš zahtev za godišnji odmor je odbijen.");
		mail.setText("Pozdrav " + doctor.getName() + ",\nVaš zahtev za godišnji odmor je odbijen.\nObrazloženje:\n" + message);
		javaMailSender.send(mail);
		
		System.out.println("Email je poslat!");
	}
	
	//Emails for absence request for nurses
	@Async
	public void sendNotificationAcceptNurse(Nurse nurse) throws MailException, InterruptedException{
		
		System.out.println("Slanje emaila... ");
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Vaš zahtev za godišnji odmor je prihvaćen");
		mail.setText("Pozdrav " + nurse.getName() + ",\nVaš zahtev za godišnji odmor je prihvaćen.");
		javaMailSender.send(mail);
		
		System.out.println("Email je poslat!");
	}
	
	@Async
	public void sendNotificationDeclineNurse(Nurse nurse, String message) throws MailException, InterruptedException{
		
		System.out.println("Slanje emaila...");
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Vaš zahtev za godišnji odmor je odbijen.");
		mail.setText("Pozdrav " + nurse.getName() + ",\nVaš zahtev za godišnji odmor je odbijen.\nObrazloženje:\n" + message);
		javaMailSender.send(mail);
		
		System.out.println("Email je poslat!");
	}
	
	// Email which doctor send clinic admin when he wants to schedule an examination 
	@Async
	public void sendRequestForSchedulingExamination(Examination examination) throws MailException, InterruptedException{
		
		System.out.println("Slanje emaila... ");
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Molim vas potvrdite zakazivanje pregleda");
		if(examination.getOperation() == true) {
			mail.setText("Pozdrav dragi administratore klinike, želeo bih da zakazem operaciju za pacijenta.\n\n Na sledećem linku se nalaze informacije "
					+ "o operaciji koju želim zakazati:\n" + "http://localhost:8080/bookOperationRoom/"+ examination.getId());
		} else {
			mail.setText("Pozdrav dragi administratore klinike, želeo bih da zakazem pregled za pacijenta.\n\n Na sledećem linku se nalaze informacije "
					+ "o pregledu koji želim zakazati:\n" + "http://localhost:8080/bookExaminationRoom/"+ examination.getId());
		}
	
		javaMailSender.send(mail);
		
		System.out.println("Email je poslat!");
	}
	
	// E-mails sent to doctor and patient when doctors request for examination is confirmed by clinic administrator
	@Async 
	public void sendNotificationExaminationDoctor(Examination examination) throws MailException, InterruptedException {
		System.out.println("Slanje emaila... ");
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Vaš zahtev za pregled je potvrđen");
		mail.setText("Pozdrav " + examination.getDoctor().getName() + ",\nVaš zahtev za zakazivanje pregleda je prihvaćen.\n\n "
				+ "Pregled za pacijenta " + examination.getPatient().getName() + " " + examination.getPatient().getSurname() + " je zakazan za " + examination.getDate() + 
				" u " + examination.getStartTime()/60 + "h i " + examination.getStartTime()%60 + "min u sali " + examination.getRoom().getRoomCodeName() + ".");
		javaMailSender.send(mail);
		
		System.out.println("Email je poslat!");
	}
	
	@Async
	public void sendNotificationExaminationPatient(Examination examination) throws MailException, InterruptedException{
		System.out.println("Slanje emaila... ");
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("isapswgrupa11@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Vaš doktor je zakazao novi pregled za vas");
		mail.setText("Pozdrav " + examination.getPatient().getName() + ",\nZakazan vam je novi pregled kod lekara " + 
				 examination.getDoctor().getName() + " " + examination.getDoctor().getSurname() + " za " + examination.getDate() + 
				" u " + examination.getStartTime()/60 + "h i " + examination.getStartTime()%60 + "min u sali " + examination.getRoom().getRoomCodeName() + ".");
		javaMailSender.send(mail);
		
		System.out.println("Email je poslat!");
	}
	

}