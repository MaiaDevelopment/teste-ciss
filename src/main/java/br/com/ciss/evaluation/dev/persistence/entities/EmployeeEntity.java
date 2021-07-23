package br.com.ciss.evaluation.dev.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.caelum.stella.bean.validation.NIT;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull(message = "Nome deve ser informado")
	@Size(min=2, max=30, message = "Nome deve ser entre 2 e 30 caracteres")
	private String firstName;
	@NotNull(message = "Sobrenome deve ser informado")
	@Size(min=2, max=50, message = "Sobrenome deve ser entre 2 e 50 caracteres")
	private String lastName;
	@Email(message = "Formato do e-mail é inválido")
	private String email;
	//@Size(max=10, message = "PIS deve ter no máximo 10 dígitos")
	//@Pattern(regexp = "-?[0-9]+", message = "PIS deve conter somente números")
	@NIT
	private String pisNumber;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeEntity other = (EmployeeEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}


