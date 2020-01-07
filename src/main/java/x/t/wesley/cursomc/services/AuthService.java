package x.t.wesley.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Cliente;
import x.t.wesley.cursomc.repositories.ClienteRepository;
import x.t.wesley.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository cliRep;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {
		
		Cliente cliente = cliRep.findByEmail(email);
		
		if(cliente==null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		
		cliente.setSenha(pe.encode(newPass));
		
		cliRep.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
		

	}

	private String newPassword() {
		char[] vet = new char[10];

		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}

		return new String(vet);

	}

	private char randomChar() {

		int opt = rand.nextInt();

		if (opt == 0) {// Gera um digito
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) {// Gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else {// Gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
