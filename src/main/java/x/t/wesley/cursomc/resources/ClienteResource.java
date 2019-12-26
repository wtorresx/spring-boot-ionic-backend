package x.t.wesley.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import x.t.wesley.cursomc.domain.Cliente;
import x.t.wesley.cursomc.dto.ClienteDTO;
import x.t.wesley.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService cliServ;

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> Clientes() {
		List<Cliente> clientes = cliServ.getClientes();

		List<ClienteDTO> clientesDTO = clientes.stream().map(cliente -> new ClienteDTO(cliente))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(clientesDTO);
	}
	
	@GetMapping(value="/page")
	public ResponseEntity<Page<ClienteDTO>> ClientesPage(
			@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value="direction", defaultValue = "ASC") String direction) {
		
		Page<Cliente> clientes = cliServ.getClientesPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> clientesDTO = clientes.map(cliente -> new ClienteDTO(cliente));
		return ResponseEntity.ok().body(clientesDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> Cliente(@PathVariable("id") Integer id) {
		Cliente cliente = cliServ.getCliente(id);
		return ResponseEntity.ok().body(cliente);
	}
		
	@PostMapping
	public ResponseEntity<Void> novaCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
		
		Cliente cliente = cliServ.fromDTO(clienteDTO);		
		cliente = cliServ.postCliente(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> editarCliente(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {
		
		Cliente cliente = cliServ.fromDTO(clienteDTO);
		
		cliente.setId(id);
		cliente = cliServ.putCliente(cliente);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletarCliente(@PathVariable Integer id) {
		cliServ.deleteCliente(id);
		return ResponseEntity.noContent().build();
	}
	

}
