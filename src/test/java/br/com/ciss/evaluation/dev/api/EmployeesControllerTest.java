package br.com.ciss.evaluation.dev.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.ciss.evaluation.dev.persistence.entities.EmployeeEntity;
import br.com.ciss.evaluation.dev.persistence.repositories.EmployeeRepository;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeesControllerTest {
	
	private final String PATH = "/api/employees";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeRepository repository;
	
	@BeforeEach
	public void setup() {
		EmployeeEntity entity = EmployeeEntity.builder()
				.firstName("nomeMock1")
				.lastName("nomeMock2")
				.email("nomeMock@email.com")
				.pisNumber("000")
				.id(1)
				.build();
		
		when(repository.findById(1)).thenReturn(Optional.of(entity));
		when(repository.existsById(1)).thenReturn(Boolean.TRUE);
		when(repository.save(any(EmployeeEntity.class))).thenReturn(entity);
		doNothing().when(repository).deleteById(1);
	}

	@Test
	void givenValidIdOnGet_whenMockMVC_thenResponseOk() throws Exception {
		mockMvc.perform(
				get(PATH + "/1")
			    .contentType(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.firstName", is("nomeMock1")))
			    .andExpect(jsonPath("$.lastName", is("nomeMock2")))
			    .andExpect(jsonPath("$.email", is("nomeMock@email.com")))
			    .andExpect(jsonPath("$.pisNumber", is("000")))
			    .andDo(print());
	}
	
	@Test
	void givenInvalidId_whenMockMVC_thenResponseNotFound() throws Exception {
		mockMvc.perform(
				get(PATH + "/2")
			    .contentType(MediaType.APPLICATION_JSON))
			    .andExpect(status().isNotFound())
			    .andExpect(content().string("Não localizado funcionário com id 2"))
			    .andDo(print());
	}

	@Test
	void givenValidFieldsOnPost_whenMockMVC_thenResponseCreated() throws Exception {
		mockMvc.perform(
				post(PATH)
			    .contentType(MediaType.APPLICATION_JSON)
			    .content("{ \"firstName\": \"nomeMock1\", \"lastName\": \"nomeMock2\", \"email\": \"nomeMock@email.com\" }"))
			    .andExpect(status().isCreated())
			    .andExpect(header().string("Location", "/api/employees/1"))
			    .andExpect(jsonPath("$.firstName", is("nomeMock1")))
			    .andExpect(jsonPath("$.lastName", is("nomeMock2")))
			    .andExpect(jsonPath("$.email", is("nomeMock@email.com")))
			    .andExpect(jsonPath("$.pisNumber", is("000")))
			    .andDo(print());
	}

	@Test
	void givenValidFieldsOnPut_whenMockMVC_thenResponseOK() throws Exception {
		mockMvc.perform(
				put(PATH + "/1")
			    .contentType(MediaType.APPLICATION_JSON)
			    .content("{ \"firstName\": \"nomeMock1\", \"lastName\": \"nomeMock2\", \"email\": \"nomeMock@email.com\" }"))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.firstName", is("nomeMock1")))
			    .andExpect(jsonPath("$.lastName", is("nomeMock2")))
			    .andExpect(jsonPath("$.email", is("nomeMock@email.com")))
			    .andExpect(jsonPath("$.pisNumber").isEmpty())
			    .andDo(print());
	}

	@Test
	void givenValidIdOnDelete_whenMockMVC_thenResponseOk() throws Exception {
		mockMvc.perform(
				delete(PATH + "/1")
			    .contentType(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andDo(print());
	}
	
	@Test
	void givenInvalidIdOnDelete_whenMockMVC_thenResponseOk() throws Exception {
		mockMvc.perform(
				delete(PATH + "/2")
			    .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
			    .andExpect(content().string("Não localizado funcionário com id 2"))
			    .andDo(print());
	}

}
