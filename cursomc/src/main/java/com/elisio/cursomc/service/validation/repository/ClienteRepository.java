package com.elisio.cursomc.service.validation.repository;


import com.elisio.cursomc.domain.Categoria;
import com.elisio.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Transactional(readOnly = true)//Esta sendo usado para validação de email
    Cliente findByEmail(String email);
}
