package br.com.ciss.evaluation.dev.services;

import br.com.ciss.evaluation.dev.share.dtos.EmployeeDTO;
import br.com.ciss.evaluation.dev.share.exceptions.RecordNotFoundException;
import br.com.ciss.evaluation.dev.share.exceptions.ServiceException;

public interface EmployeeService {

	EmployeeDTO create(String firstName, String lastName, String email, String pisNumber) throws ServiceException;
	
	EmployeeDTO update(EmployeeDTO employee) throws ServiceException, RecordNotFoundException;
	
	void remove(Integer employeeId) throws ServiceException, RecordNotFoundException;
	
	EmployeeDTO findById(Integer employeeId) throws ServiceException, RecordNotFoundException;
	
}
