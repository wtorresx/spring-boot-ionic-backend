package x.t.wesley.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import x.t.wesley.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String emailFrom;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		
		SimpleMailMessage email = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(email);
		
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		
		SimpleMailMessage email = new SimpleMailMessage();
		
		email.setTo(pedido.getCliente().getEmail());
		email.setFrom(emailFrom);
		email.setSubject("Pedido confirmado! Código: " + pedido.getId());
		email.setSentDate(new Date(System.currentTimeMillis()));
		email.setText(pedido.toString());
		
		return email;
	}
}
