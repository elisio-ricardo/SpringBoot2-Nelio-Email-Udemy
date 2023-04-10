package com.elisio.cursomc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
//    @Autowired
//    private S3Service s3Service;

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //Se tivesse conta ativa e diretorio correto iria fazer o upload
        //esse seria um teste para subir a partir do C:
       // s3Service.uploadFile("C:\\nome\\do\\diretorio\\das\\imagens.jpg");
    }
}
