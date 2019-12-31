package x.t.wesley.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import x.t.wesley.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	void sendEmail(SimpleMailMessage email);
	
}
