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
import java.util.Calendar;
import java.util.Date;
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
import jpa.model.Examination;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClinicControllerTest {
	public static final Long DB_ID = 1L;
	private static final String URL_PREFIX = "/api/clinics";
	public static final Calendar dateCalendar;
	static {
	    dateCalendar = GregorianCalendar.getInstance();
	    dateCalendar.clear();
	    dateCalendar.set(2020, 5, 1);
	}
	public static final Date date1 = dateCalendar.getTime();
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
	public void testGetAllFilteredClinics() throws Exception {
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("role", "PATIENT");
		mockMvc.perform(get(URL_PREFIX + "/allFiltered/" +new java.sql.Date(120, 01, 05)+"/"+"ocn").sessionAttrs(sessionattr)).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(1)))
		.andExpect(jsonPath("$.[*].name").value(hasItem("Urologija")))
		.andExpect(jsonPath("$.[*].adress").value(hasItem("blabla")))
		.andExpect(jsonPath("$.[*].description").value(hasItem("opis")));
		

	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateExamination() throws Exception {
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("role", "PATIENT");
		mockMvc.perform(get(URL_PREFIX + "/select/" +new java.sql.Date(120, 01, 05)+"/"+"ocn/"+DB_ID).sessionAttrs(sessionattr)).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(1)))
		.andExpect(jsonPath("$.[*].name").value(hasItem("DrMr")))
		.andExpect(jsonPath("$.[*].surname").value(hasItem("MrDr")))
		.andExpect(jsonPath("$.[*].email").value(hasItem("drmd.com")))
		.andExpect(jsonPath("$.[*].adress").value(hasItem("NTP")))
		.andExpect(jsonPath("$.[*].city").value(hasItem("Nju Nau")))
		.andExpect(jsonPath("$.[*].state").value(hasItem("Makedonija")))
		.andExpect(jsonPath("$.[*].phone").value(hasItem(333)));
		

	}
	
	
	
	
	
	

}
