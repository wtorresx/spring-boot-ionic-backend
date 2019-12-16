package x.t.wesley.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Cliente;
import x.t.wesley.cursomc.repositories.ClienteRepository;
import x.t.wesley.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cliRep;

	public List<Cliente> getClientes() {
		return cliRep.findAll();
	}

	public Cliente getCliente(Integer id) {
		Optional<Cliente> cliente = cliRep.findById(id);

		if (cliente.orElse(null) == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
		;

		return cliente.orElse(null);
	}

	public void postCliente(Cliente cliente) {
		cliRep.save(cliente);
	}

	public void postClientes(List<Cliente> clientes) {
		cliRep.saveAll(clientes);
	}

}
