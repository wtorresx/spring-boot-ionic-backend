package x.t.wesley.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage email) {
		LOG.info("Enviando de e-mail...");
		mailSender.send(email);
		LOG.info("E-mail enviado");
	}

	@Override
	public void sendHtmlEmail(MimeMessage email) {
		LOG.info("Enviando de e-mail HTML...");
		javaMailSender.send(email);
		LOG.info("E-mail enviado");		
	}

}
