package jpa.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import jpa.TestUtil;
import jpa.constants.ExaminationConstants;
import jpa.model.Examination;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExaminationControllerTest {
	private static final String URL_PREFIX = "/api/examinations";
	public static final Long DB_ID = 1L;
	public static final String DB_TYPE = "Ocni pregled";
	public static final String DB_D_NAME = "DrMr";
	public java.sql.Date date = new java.sql.Date(2020, 02, 01);
	public static final Integer DB_START_TIME = 420;
	public static final Integer DB_END_TIME = 480;
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype());

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetAllNonReserved() throws Exception {
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("role", "PATIENT");
		mockMvc.perform(get(URL_PREFIX + "/nonReserved").sessionAttrs(sessionattr)).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(2)))
	//	.andExpect( jsonPath("$.[*].date").value(hasItem(date)))
		.andExpect(jsonPath("$.[*].startTime").value(hasItem(440)))
		.andExpect(jsonPath("$.[*].endTime").value(hasItem(490)))
		.andExpect(jsonPath("$.[*].price").value(hasItem(1600.0)))
		.andExpect(jsonPath("$.[*].type.typeName").value(hasItem(DB_TYPE)))
		.andExpect(jsonPath("$.[*].doctor.name").value(hasItem(DB_D_NAME)));

	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetAllExaminationssPatient() throws Exception {
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("id", DB_ID);
		mockMvc.perform(get(URL_PREFIX + "/allByPatient").sessionAttrs(sessionattr)).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(1)))
	//	.andExpect(jsonPath("$.[*].date").value(hasItem(date)))
		.andExpect(jsonPath("$.[*].startTime").value(hasItem(420)))
		.andExpect(jsonPath("$.[*].endTime").value(hasItem(480)))
		.andExpect(jsonPath("$.[*].price").value(hasItem(1500.0)))
		.andExpect(jsonPath("$.[*].type.typeName").value(hasItem(DB_TYPE)))
		.andExpect(jsonPath("$.[*].doctor.name").value(hasItem(DB_D_NAME)));

	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetAllExaminationss() throws Exception {
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("role", "PATIENT");
		sessionattr.put("selectedClinicId", DB_ID);
		mockMvc.perform(get(URL_PREFIX + "/allByClinic").sessionAttrs(sessionattr)).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(2)))
	//	.andExpect( jsonPath("$.[*].date").value(hasItem(date)))
		.andExpect(jsonPath("$.[*].startTime").value(hasItem(440)))
		.andExpect(jsonPath("$.[*].endTime").value(hasItem(490)))
		.andExpect(jsonPath("$.[*].price").value(hasItem(1600.0)))
		.andExpect(jsonPath("$.[*].type.typeName").value(hasItem(DB_TYPE)))
		.andExpect(jsonPath("$.[*].doctor.name").value(hasItem(DB_D_NAME)));

	}
	
	@Test
	public void testGettingExaminations() throws Exception{
		mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(ExaminationConstants.DB_ID.intValue())))
		//.andExpect(jsonPath("$.[*].date").value(hasItem(new java.sql.Date(dateCalendarNew.getTimeInMillis()))))
		.andExpect(jsonPath("$.[*].startTime").value(hasItem(DB_START_TIME)))
		.andExpect(jsonPath("$.[*].endTime").value(hasItem(DB_END_TIME)));
	}
	
	
	
	
}
