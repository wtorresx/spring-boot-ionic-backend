package x.t.wesley.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import x.t.wesley.cursomc.domain.Cidade;
import x.t.wesley.cursomc.domain.Cliente;
import x.t.wesley.cursomc.domain.Endereco;
import x.t.wesley.cursomc.domain.enums.TipoCliente;
import x.t.wesley.cursomc.dto.ClienteDTO;
import x.t.wesley.cursomc.dto.ClienteNewDTO;
import x.t.wesley.cursomc.repositories.ClienteRepository;
import x.t.wesley.cursomc.repositories.EnderecoRepository;
import x.t.wesley.cursomc.services.exceptions.DataIntegrityException;
import x.t.wesley.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cliRep;

	@Autowired
	private EnderecoRepository endRep;

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

	@Transactional
	public Cliente postCliente(Cliente cliente) {
		// Garantir que será um novo registro!
		cliente.setId(null);
		
		endRep.saveAll(cliente.getEnderecos());
		
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

	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {

		Cliente cli = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(clienteNewDTO.getTipo()));
		Cidade cid = new Cidade(clienteNewDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(),
				clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cid, cli);

		cli.getEnderecos().add(end);

		for (String x : clienteNewDTO.getTelefones()) {
			end.getTelefones().add(x);
		}

		return cli;
	}

	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}

}
