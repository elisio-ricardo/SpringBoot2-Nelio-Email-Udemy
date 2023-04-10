package com.elisio.cursomc.resourceController;


import com.elisio.cursomc.DTO.ProdutoDTO;
import com.elisio.cursomc.domain.Produto;
import com.elisio.cursomc.resourceController.utils.URL;
import com.elisio.cursomc.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) {
        Produto obj = service.fendById(id);

        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/")
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        String nomeDecode = URL.decodeParam(nome);//Remove os espaços e altera os caracteres especiais
        List<Long> ids = URL.decodeintList(categorias); //Pega todas categorias da URI
        Page<Produto> list = service.search(nomeDecode, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }

}
