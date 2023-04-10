package com.elisio.cursomc.service.validation;

import com.elisio.cursomc.DTO.ClienteNewDTO;
import com.elisio.cursomc.domain.Cliente;
import com.elisio.cursomc.enums.TipoCliente;
import com.elisio.cursomc.service.validation.repository.ClienteRepository;
import com.elisio.cursomc.resourceController.exceptions.FieldMessage;
import com.elisio.cursomc.service.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert cli) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (objDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDTO.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF invalido"));
        }


        if (objDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDTO.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF invalido"));
        }

        Cliente aux = clienteRepository.findByEmail(objDTO.getEmail());
        if(aux != null){
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
