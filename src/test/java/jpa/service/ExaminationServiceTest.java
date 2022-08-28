package jpa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import static jpa.constants.ExaminationConstants.DB_ID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpa.model.Examination;
import jpa.model.Patient;
import jpa.repository.ExaminationRepository;
import jpa.repository.PatientRepository;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ExaminationServiceTest {
	@Mock
	private ExaminationRepository examinationRepositoryMock;
	@Mock
	private Examination examinationMock;
	@InjectMocks
	private ExaminationService examinationService;
	@Mock
	private PatientRepository patientRepositoryMock;
	@Mock
	private Patient patientMock;
	@InjectMocks
	private PatientService patientService;
	@Test 
	public void testFindOne() {
		
		when(examinationRepositoryMock.findOneById(35L)).thenReturn(examinationMock);
		
		Examination dbExamination = examinationService.findOne(35L);
		assertEquals(examinationMock, dbExamination);
		
        verify(examinationRepositoryMock, times(1)).findOneById(35L);
        verifyNoMoreInteractions(examinationRepositoryMock);
	}
	
	@Test
	@SuppressWarnings("deprecation")
	@Transactional
    @Rollback(true)
	public void testUpdate() {
		
		when(examinationRepositoryMock.findOneById(35L)).thenReturn(new Examination(35L, new java.sql.Date(2020, 02, 01),420,480, 1500,true,true ));
		Examination dbExamination = examinationService.findOne(35L);
		
		when(patientRepositoryMock.findOneById(2L)).thenReturn(patientMock);
		
		Patient dbPatient = patientService.findOne(2L);
		assertEquals(patientMock, dbPatient);
		
		dbExamination.setPatient(patientMock);;
		
		
		
		
		when(examinationRepositoryMock.save(dbExamination)).thenReturn(dbExamination);
		dbExamination = examinationService.save(dbExamination);
		assertThat(dbExamination).isNotNull();
		
		//verify that database contains updated data
		dbExamination = examinationService.findOne(35L);
        assertThat(dbExamination.getPatient()).isEqualTo(patientMock);

        verify(examinationRepositoryMock, times(2)).findOneById(35L);
        verify(examinationRepositoryMock, times(1)).save(dbExamination);
        verifyNoMoreInteractions(examinationRepositoryMock);
	}
	
}
