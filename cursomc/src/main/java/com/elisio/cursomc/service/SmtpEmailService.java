package com.elisio.cursomc.service;

import com.elisio.cursomc.config.EmailConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;
//Estava usando essa implementação mas começou a dar erro e parei,
//    @Autowired
//    private JavaMailSender javaMailSender;

    @Autowired
    private EmailConfig emailConfig;

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("Enviando  email..");
        mailSender.send(msg);
        LOG.info("Email Enviado para gmail");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        LOG.info("Simulando envio de email HTML..");
        emailConfig.mailSender().send(msg);
        LOG.info("Email Enviado");
    }

}
