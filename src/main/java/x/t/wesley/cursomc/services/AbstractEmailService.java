package x.t.wesley.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import x.t.wesley.cursomc.domain.Cliente;
import x.t.wesley.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

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

	protected String htmlFromTemplatePedido(Pedido pedido) {
		Context context = new Context();
		context.setVariable("pedido", pedido);

		return templateEngine.process("email/confirmacaoPedido", context);

	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {

		try {
			MimeMessage email = prepareSimpleMimeMessageFromPedido(pedido);
			sendHtmlEmail(email);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(pedido);
		}

	}

	private MimeMessage prepareSimpleMimeMessageFromPedido(Pedido pedido) throws MessagingException {

		MimeMessage email = javaMailSender.createMimeMessage();
		MimeMessageHelper emailHelper = new MimeMessageHelper(email, true);
		emailHelper.setTo(pedido.getCliente().getEmail());
		emailHelper.setFrom(emailFrom);
		emailHelper.setSubject("Pedido confirmado! Código: " + pedido.getId());
		emailHelper.setSentDate(new Date(System.currentTimeMillis()));
		emailHelper.setText(htmlFromTemplatePedido(pedido), true);

		return email;
	}

	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage email = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(email);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage email = new SimpleMailMessage();

		email.setTo(cliente.getEmail());
		email.setFrom(emailFrom);
		email.setSubject("Solicitação de nova senha");
		email.setSentDate(new Date(System.currentTimeMillis()));
		email.setText("Nova senha: " + newPass);

		return email;
	}
}
