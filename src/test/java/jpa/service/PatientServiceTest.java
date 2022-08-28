package jpa.service;

import static jpa.constants.ExaminationConstants.DB_ACCEPTED;
import static jpa.constants.ExaminationConstants.DB_END_TIME;
import static jpa.constants.ExaminationConstants.DB_ID;
import static jpa.constants.ExaminationConstants.DB_OPERATION;
import static jpa.constants.ExaminationConstants.DB_PRICE;
import static jpa.constants.ExaminationConstants.DB_START_TIME;
import static jpa.constants.ExaminationConstants.dateCalendar;
import static jpa.constants.ExaminationConstants.dateCalendarNew;
import static jpa.constants.ExaminationConstants.NEW_START_TIME;
import static jpa.constants.ExaminationConstants.NEW_END_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jpa.model.Examination;
import jpa.repository.ExaminationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {

	@Mock 
	private ExaminationRepository examinationRepositoryMock;
	
	@Mock
	private Examination examinationMock;
	
	@InjectMocks
	private ExaminationService examinationService;
	
	@Test
	public void testFindAll() {
		when(examinationRepositoryMock.findAll()).thenReturn(Arrays.asList(new Examination(DB_ID, new java.sql.Date(dateCalendar.getTimeInMillis()), DB_START_TIME, DB_END_TIME, DB_PRICE, DB_ACCEPTED, DB_OPERATION)));
		List<Examination> examinations = examinationService.findAll();
		assertThat(examinations).hasSize(1);
		
		verify(examinationRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(examinationRepositoryMock);
	}
	
	@Test
	public void testAdd() {
		when(examinationRepositoryMock.findAll()).thenReturn(Arrays.asList(new Examination (DB_ID, new java.sql.Date(dateCalendar.getTimeInMillis()), DB_START_TIME, DB_END_TIME, DB_PRICE, DB_ACCEPTED, DB_OPERATION)));
		
		Examination examination = new Examination();
		examination.setDate(new java.sql.Date(dateCalendarNew.getTimeInMillis()));
		examination.setStartTime(NEW_START_TIME);
		examination.setEndTime(NEW_END_TIME);
		
		when(examinationRepositoryMock.save(examination)).thenReturn(examination);
		
		int dbSizeBeforeAdd = examinationService.findAll().size();
		
		Examination dbExamination = examinationService.save(examination);
		assertThat(dbExamination).isNotNull();
		
		when(examinationRepositoryMock.findAll()).thenReturn(Arrays.asList(new Examination(DB_ID, new java.sql.Date(dateCalendar.getTimeInMillis()), DB_START_TIME, DB_END_TIME, DB_PRICE, DB_ACCEPTED, DB_OPERATION), examination));
		//Validate that the new examination in database
		List<Examination> examinations = examinationService.findAll();
		assertThat(examinations).hasSize(dbSizeBeforeAdd + 1);
        dbExamination = examinations.get(examinations.size() - 1); //get last examination
        assertThat(dbExamination.getDate()).isEqualTo(new java.sql.Date(dateCalendarNew.getTimeInMillis()));
        assertThat(dbExamination.getStartTime()).isEqualTo(NEW_START_TIME);
        assertThat(dbExamination.getEndTime()).isEqualTo(NEW_END_TIME);
        verify(examinationRepositoryMock, times(2)).findAll();
        verify(examinationRepositoryMock, times(1)).save(examination);
        verifyNoMoreInteractions(examinationRepositoryMock);
	}
	
}
