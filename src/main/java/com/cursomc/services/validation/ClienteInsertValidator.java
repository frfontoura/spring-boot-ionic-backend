package com.cursomc.services.validation;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cursomc.domain.Cliente;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.resources.exception.FieldMessage;
import com.cursomc.services.validation.utils.CpfCnpjUtils;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> fieldMessages = new ArrayList<>();
		TipoCliente tipoCliente = TipoCliente.valueOf(objDto.getTipo());
		
		if (TipoCliente.PESSOA_FISICA.equals(tipoCliente) && !CpfCnpjUtils.isValidCPF(objDto.getCpfOuCnpj())) {
			fieldMessages.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (TipoCliente.PESSOA_JURIDICA.equals(tipoCliente) && !CpfCnpjUtils.isValidCNPJ(objDto.getCpfOuCnpj())) {
			fieldMessages.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		if (aux != null) {
			fieldMessages.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : fieldMessages) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return fieldMessages.isEmpty();
	}
}