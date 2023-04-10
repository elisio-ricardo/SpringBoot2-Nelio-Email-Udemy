package com.elisio.cursomc.service;


import com.elisio.cursomc.domain.ItemPedido;
import com.elisio.cursomc.service.validation.exceptions.ObjectNotFoundException;
import com.elisio.cursomc.service.validation.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository repository;

    public ItemPedido findById(Long id) {
        Optional<ItemPedido> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException
                ("Objeto não encontrado! Id: " +
                        id + " Tipo: " + ItemPedido.class.getName()));
    }

    public ItemPedido insert(ItemPedido obj) {
        obj.setId(null);
        return repository.save(obj);
    }

    public List<ItemPedido> insertAll(List<ItemPedido> obj) {
        return repository.saveAll(obj);
    }
}
