package com.elisio.cursomc.service;


import com.elisio.cursomc.domain.Cliente;
import com.elisio.cursomc.service.validation.exceptions.ObjectNotFoundException;
import com.elisio.cursomc.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {
    //Classe para geração de nova senha
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void sendNewPassword(String email) {
        Cliente cliente = clienteService.findByEmail(email);

        if (cliente == null) {
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassaword();
        cliente.setSenha(encoder.encode(newPass));

        clienteRepository.save(cliente);
       emailService.sendNewPasswordEmail(cliente, newPass);

    }

    private String newPassaword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() { //gerador de senha letras minusculas, maiusculas e numeros
        int opt = random.nextInt(3); //gerador de 0 a 3

        if (opt == 0) { //gera um digito
            return (char) (random.nextInt(10) + 48); //olhou na tabela unicode os numeros começam apartir do 48, somando o valor dos 10 digitos
        } else if (opt == 1) {//gera letra maiuscula
            return (char) (random.nextInt(26) + 65); //começa no 65+ 26 letras
        } else {// //gera letra minuscula
            return (char) (random.nextInt(26) + 97);
        }
    }
}
