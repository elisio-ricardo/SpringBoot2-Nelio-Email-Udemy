package com.elisio.cursomc.service;

import com.elisio.cursomc.domain.Pagamento;
import com.elisio.cursomc.service.validation.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PagamentoService {

    @Autowired
    public PagamentoRepository repository;

    public Pagamento insert(Pagamento obj) {
        obj.setId(null);
        return repository.save(obj);
    }
}
