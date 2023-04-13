package com.elisio.cursomc.config;

import com.elisio.cursomc.service.DBService;
import com.elisio.cursomc.service.EmailService;
import com.elisio.cursomc.service.MockEmailService;
import com.elisio.cursomc.service.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDataBase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }

    @Bean
    public EmailService emailServiceSmtp() {
        return new SmtpEmailService();
    }

}
