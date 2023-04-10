package com.elisio.cursomc.service.validation.repository;


import com.elisio.cursomc.domain.Categoria;
import com.elisio.cursomc.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
