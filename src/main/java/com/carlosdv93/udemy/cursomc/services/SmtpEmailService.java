package com.carlosdv93.udemy.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {
	
	@Autowired
	private MailSender MailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
	
		LOG.info("Enviado o Email........");
		MailSender.send(msg);
		LOG.info("Email Enviado com sucesso");
		
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		
		LOG.info("Enviado o Email HTML........");
		javaMailSender.send(msg);
		LOG.info("Email HTML Enviado com sucesso");
		
	}

}
