package com.elisio.cursomc.service;


import com.elisio.cursomc.domain.Categoria;
import com.elisio.cursomc.domain.Produto;
import com.elisio.cursomc.service.validation.exceptions.ObjectNotFoundException;
import com.elisio.cursomc.service.validation.repository.CategoriaRepository;
import com.elisio.cursomc.service.validation.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto fendById(Long id) {
        Optional<Produto> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException
                ("Objeto não encontrado! Id: " +
                        id + " Tipo: " + Produto.class.getName()));
    }

    public Page<Produto> search(String nome, List<Long> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);

        return repository.search(nome, categorias, pageRequest);
    }

}
