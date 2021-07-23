package br.com.ciss.evaluation.dev.api;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ciss.evaluation.dev.services.EmployeeService;
import br.com.ciss.evaluation.dev.share.dtos.EmployeeDTO;
import br.com.ciss.evaluation.dev.share.exceptions.RecordNotFoundException;
import br.com.ciss.evaluation.dev.share.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/employees")
@Slf4j
public class EmployeesController {

	@Autowired
	private EmployeeService service;
	
	@GetMapping("/{id}")
	public ResponseEntity getEmployee(@PathVariable Integer id) {
		
		try {
			EmployeeDTO dto = service.findById(id);
			return ResponseEntity.ok(dto);
		} catch (RecordNotFoundException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} 
	}
	
	@PostMapping
	public ResponseEntity create(@RequestBody EmployeeDTO employee) throws URISyntaxException {
		try {
			EmployeeDTO dto = service.create(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPisNumber());
			return ResponseEntity.created(new URI("/api/employees/" + dto.getId())).body(dto);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity update(@PathVariable Integer id, @RequestBody EmployeeDTO employee) {
		try {
			employee.setId(id);
			EmployeeDTO dto = service.update(employee);
			return ResponseEntity.ok(dto);
		} catch (RecordNotFoundException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity remove(@PathVariable Integer id) {
		try {
			service.remove(id);
			return ResponseEntity.ok().build();
		} catch (RecordNotFoundException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} 
	}
	

}
