package br.com.ciss.evaluation.dev.share.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String pisNumber;
}
