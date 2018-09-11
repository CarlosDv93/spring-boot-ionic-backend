package com.carlosdv93.udemy.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.carlosdv93.udemy.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendConfirmationOrderEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);
			

}
