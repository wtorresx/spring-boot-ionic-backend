package x.t.wesley.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Cliente;
import x.t.wesley.cursomc.dto.ClienteDTO;
import x.t.wesley.cursomc.repositories.ClienteRepository;
import x.t.wesley.cursomc.services.exceptions.DataIntegrityException;
import x.t.wesley.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cliRep;

	public List<Cliente> getClientes() {
		return cliRep.findAll();
	}

	public Page<Cliente> getClientesPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return cliRep.findAll(pageRequest);
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

	public Cliente postCliente(Cliente cliente) {
		// Garantir que será um novo registro!
		cliente.setId(null);
		return cliRep.save(cliente);
	}

	public List<Cliente> postClientes(List<Cliente> clientes) {
		return cliRep.saveAll(clientes);
	}

	public Cliente putCliente(Cliente cliente) {

		// Garantir que a cliente existe
		Cliente newCliente = getCliente(cliente.getId());
		
		updateData(newCliente, cliente);
		return cliRep.save(newCliente);
	}

	public void deleteCliente(Integer id) {
		getCliente(id);

		try {
			cliRep.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas!");
		}
	}

	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}

}
