package com.carlosdv93.udemy.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.carlosdv93.udemy.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendConfirmationOrderEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
