package x.t.wesley.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Cliente;
import x.t.wesley.cursomc.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cliRep;

	public void postCliente(Cliente cliente) {
		cliRep.save(cliente);
	}

	public void postClientes(List<Cliente> clientes) {
		cliRep.saveAll(clientes);
	}

}
