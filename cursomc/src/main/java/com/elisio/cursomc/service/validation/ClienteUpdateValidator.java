package com.elisio.cursomc.service.validation;

import com.elisio.cursomc.DTO.ClienteDTO;
import com.elisio.cursomc.domain.Cliente;
import com.elisio.cursomc.repository.ClienteRepository;
import com.elisio.cursomc.resourceController.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate cli) {
    }

    @Override
    public boolean isValid(ClienteDTO objDTO, ConstraintValidatorContext context) {

        Map<String, String> listMap = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.valueOf(listMap.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente aux = clienteRepository.findByEmail(objDTO.getEmail());
        if (aux != null && !aux.getId().equals(uriId)) {//se o email não for nulo o id desse obj tem que ser igual ao id da URI
            list.add(new FieldMessage("email", "Email já existente"));
        }


        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldMessage())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }
}
