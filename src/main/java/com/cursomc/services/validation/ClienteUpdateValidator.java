package com.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

		List<FieldMessage> fieldMessages = new ArrayList<>();

		Cliente clienteDB = clienteRepository.findByEmail(objDto.getEmail());
		if (clienteDB != null && !clienteDB.getId().equals(uriId)) {
			fieldMessages.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage m : fieldMessages) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(m.getMessage()).addPropertyNode(m.getFieldName()).addConstraintViolation();
		}
		return fieldMessages.isEmpty();
	}
}