package br.com.ciss.evaluation.dev.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ciss.evaluation.dev.persistence.repositories.EmployeeRepository;
import br.com.ciss.evaluation.dev.share.dtos.EmployeeDTO;
import br.com.ciss.evaluation.dev.share.exceptions.RecordNotFoundException;
import br.com.ciss.evaluation.dev.share.exceptions.ServiceException;

@SpringBootTest
class EmployeeServiceTest {
	
	@Autowired 
	private EmployeeService service;
	
	@Autowired
	private EmployeeRepository repository;

	@Test @Order(1)
	void whenInformValidFieldsOnCreate() throws ServiceException {
		EmployeeDTO created = create();
		assertNotNull(created);
		assertEquals("nome1", created.getFirstName());
		assertEquals("nome2", created.getLastName());
		assertEquals("email@mail.com", created.getEmail());
		assertEquals("74836250552", created.getPisNumber());
	}
	
	@Test
	void whenInformInvalidFirstNameOnCreate() throws ServiceException {
		ServiceException exception = assertThrows(ServiceException.class, () -> {
			service.create("", "nome2", "email@mail.com", "74836250552");
		});
		assertEquals("Erro ao registrar funcionário: Nome deve ser entre 2 e 30 caracteres", exception.getMessage());
		
		ServiceException exception2 = assertThrows(ServiceException.class, () -> {
			service.create(null, "nome2", "email@mail.com", "74836250552");
		});
		assertEquals("Erro ao registrar funcionário: Nome deve ser informado", exception2.getMessage());
	}
	
	@Test
	void whenInformInvalidLastNameOnCreate() throws ServiceException {
		ServiceException exception = assertThrows(ServiceException.class, () -> {
			service.create("nome1", "", "email@mail.com", "74836250552");
		});
		assertEquals("Erro ao registrar funcionário: Sobrenome deve ser entre 2 e 50 caracteres", exception.getMessage());
		
		ServiceException exception2 = assertThrows(ServiceException.class, () -> {
			service.create("nome1", null, "email@mail.com", "74836250552");
		});
		assertEquals("Erro ao registrar funcionário: Sobrenome deve ser informado", exception2.getMessage());
	}

	@Test
	void whenInformInvalidEmailOnCreate() throws ServiceException {
		ServiceException exception = assertThrows(ServiceException.class, () -> {
			service.create("nome1", "nome2", "email", "74836250552");
		});
		assertEquals("Erro ao registrar funcionário: Formato do e-mail é inválido", exception.getMessage());
	}
	
	@Test
	void whenInformInvalidPisNumberOnCreate() throws ServiceException {
		ServiceException exception = assertThrows(ServiceException.class, () -> {
			service.create("nome1", "nome2", "email@mail.com", "0000000000");
		});
		assertEquals("Erro ao registrar funcionário: \"Não é um PIS/NIT válido\"", exception.getMessage());
		
		ServiceException exception2 = assertThrows(ServiceException.class, () -> {
			service.create("nome1", "nome2", "email@mail.com", "AAA");
		});
		assertEquals("Erro ao registrar funcionário: \"Não é um PIS/NIT válido\"", exception2.getMessage());
	}
	
	@Test
	void whenInformValidFieldsOnUpdate() throws ServiceException, RecordNotFoundException {
		EmployeeDTO created = create();
		created.setFirstName("nome3");
		EmployeeDTO updated = service.update(created);
		assertNotNull(updated);
		assertEquals("nome3", updated.getFirstName());
	}
	
	@Test
	void whenInformInvalidFirstNameOnUpdate() throws ServiceException {
		ServiceException exception = assertThrows(ServiceException.class, () -> {
			EmployeeDTO created = create();
			created.setFirstName("");
			service.update(created);
		});
		assertEquals("Erro ao atualizar funcionário: Nome deve ser entre 2 e 30 caracteres", exception.getMessage());
		
		ServiceException exception2 = assertThrows(ServiceException.class, () -> {
			EmployeeDTO created = create();
			created.setFirstName(null);
			service.update(created);
		});
		assertEquals("Erro ao atualizar funcionário: Nome deve ser informado", exception2.getMessage());
	}
	
	@Test
	void whenInformInvalidLastNameOnUpdate() throws ServiceException {
		ServiceException exception = assertThrows(ServiceException.class, () -> {
			EmployeeDTO created = create();
			created.setLastName("");
			service.update(created);
		});
		assertEquals("Erro ao atualizar funcionário: Sobrenome deve ser entre 2 e 50 caracteres", exception.getMessage());
		
		ServiceException exception2 = assertThrows(ServiceException.class, () -> {
			EmployeeDTO created = create();
			created.setLastName(null);
			service.update(created);
		});
		assertEquals("Erro ao atualizar funcionário: Sobrenome deve ser informado", exception2.getMessage());
	}

	@Test
	void whenInformInvalidEmailOnUpdate() throws ServiceException {
		ServiceException exception = assertThrows(ServiceException.class, () -> {
			EmployeeDTO created = create();
			created.setEmail("email");
			service.update(created);
		});
		assertEquals("Erro ao atualizar funcionário: Formato do e-mail é inválido", exception.getMessage());
	}
	
	@Test
	void whenInformInvalidPisNumberOnUpdate() throws ServiceException {
		ServiceException exception = assertThrows(ServiceException.class, () -> {
			EmployeeDTO created = create();
			created.setPisNumber("0000000000");
			service.update(created);
		});
		assertEquals("Erro ao atualizar funcionário: \"Não é um PIS/NIT válido\"", exception.getMessage());
	}

	@Test
	void whenInformValidIdOnDelete() throws ServiceException, RecordNotFoundException {
		EmployeeDTO created = create();
		Integer id = created.getId();
		service.remove(id);
		assertFalse(repository.existsById(id));
	}

	@Test
	void whenInformValidIdOnSearch() throws ServiceException, RecordNotFoundException {
		EmployeeDTO created = create();
		Integer id = created.getId();
		EmployeeDTO finded = service.findById(id);
		assertNotNull(finded);
	}
	
	private EmployeeDTO create() throws ServiceException {
		return service.create("nome1", "nome2", "email@mail.com", "74836250552");
	}

}
