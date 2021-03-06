package x.t.wesley.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import x.t.wesley.cursomc.domain.Cliente;
import x.t.wesley.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage email);
	
	void sendOrderConfirmationHtmlEmail(Pedido pedido);
	
	void sendHtmlEmail(MimeMessage email);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);	
}
