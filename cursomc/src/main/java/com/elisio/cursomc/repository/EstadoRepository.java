package com.elisio.cursomc.repository;


import com.elisio.cursomc.domain.Cidade;
import com.elisio.cursomc.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

    //Retorna a lista de estado ordenado por nome
    @Transactional(readOnly = true)
    public List<Estado> findAllByOrderByNome();
}
