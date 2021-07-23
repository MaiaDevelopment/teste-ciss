	package br.com.ciss.evaluation.dev.services.impls;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciss.evaluation.dev.persistence.entities.EmployeeEntity;
import br.com.ciss.evaluation.dev.persistence.repositories.EmployeeRepository;
import br.com.ciss.evaluation.dev.services.EmployeeService;
import br.com.ciss.evaluation.dev.share.dtos.EmployeeDTO;
import br.com.ciss.evaluation.dev.share.exceptions.RecordNotFoundException;
import br.com.ciss.evaluation.dev.share.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	@Autowired
    private Validator validator;

	@Override
	public EmployeeDTO create(String firstName, String lastName, String email, String pisNumber) throws ServiceException {
		
		EmployeeEntity entity = EmployeeEntity.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.pisNumber(pisNumber)
				.build();

		try {
			validateEntity(entity);
			entity = repository.save(entity);
		} catch (Exception ex) {
			throw new ServiceException("Erro ao registrar funcionário: " + ex.getMessage(), ex);
		}
		
		return EmployeeDTO.builder()
				.id(entity.getId())
				.firstName(entity.getFirstName())
				.lastName(entity.getLastName())
				.email(entity.getEmail())
				.pisNumber(entity.getPisNumber())
				.build(); 
	}

	@Override
	public EmployeeDTO update(EmployeeDTO employee) throws ServiceException, RecordNotFoundException {		
		if (employee == null)
			throw new ServiceException("Funcionário deve ser informado");
		
		Integer employeeId = employee.getId();
		if (employeeId == null)
			throw new ServiceException("ID do funcionário deve ser informado");
		
		EmployeeEntity entity = null;
		try {
			entity = repository.findById(employeeId).orElse(null);
			if (entity == null)
				throw new RecordNotFoundException("Não localizado funcionário com id " + employeeId);
		} catch (Exception ex) {
			throw new ServiceException("Erro ao buscar funcionário com id " + employeeId, ex);
		}
		
		entity.setFirstName(employee.getFirstName());
		entity.setLastName(employee.getLastName());
		entity.setEmail(employee.getEmail());
		entity.setPisNumber(employee.getPisNumber());
		
		try {
			validateEntity(entity);
			entity = repository.save(entity);
		} catch (Exception ex) {
			throw new ServiceException("Erro ao atualizar funcionário: " + ex.getMessage(), ex);
		}
		
		return EmployeeDTO.builder()
				.id(entity.getId())
				.firstName(entity.getFirstName())
				.lastName(entity.getLastName())
				.email(entity.getEmail())
				.pisNumber(entity.getPisNumber())
				.build();
	}

	@Override
	public void remove(Integer employeeId) throws ServiceException, RecordNotFoundException {
		if (employeeId == null)
			throw new ServiceException("ID do funcionário deve ser informado");
		
		try {
			boolean exists = repository.existsById(employeeId);
			if (!exists)
				throw new RecordNotFoundException("Não localizado funcionário com id " + employeeId);
		} catch (RecordNotFoundException ex) {
			throw ex;
		}  catch (Exception ex) {
			throw new ServiceException("Erro ao buscar funcionário com id " + employeeId, ex);
		}
		
		try {
			repository.deleteById(employeeId);
		} catch (Exception ex) {
			throw new ServiceException("Erro ao atualizar funcionário.", ex);
		}
	}

	@Override
	public EmployeeDTO findById(Integer employeeId) throws ServiceException, RecordNotFoundException {
		if (employeeId == null)
			throw new ServiceException("ID do funcionário deve ser informado");
		
		try {
			EmployeeEntity entity = repository.findById(employeeId).orElse(null);
			if (entity == null)
				throw new RecordNotFoundException("Não localizado funcionário com id " + employeeId);
		
		
			return EmployeeDTO.builder()
					.id(entity.getId())
					.firstName(entity.getFirstName())
					.lastName(entity.getLastName())
					.email(entity.getEmail())
					.pisNumber(entity.getPisNumber())
					.build();
		} catch (RecordNotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException("Erro ao buscar funcionário com id " + employeeId, ex);
		}
	}
	
	private void validateEntity(EmployeeEntity entity) {
		Set<ConstraintViolation<EmployeeEntity>> violations = validator.validate(entity);
		if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<EmployeeEntity> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            log.error(sb.toString());
            throw new ConstraintViolationException(sb.toString(), violations);
        } 
	}

}

	