package x.t.wesley.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import x.t.wesley.cursomc.domain.Cliente;
import x.t.wesley.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService cliServ;

	@GetMapping
	public ResponseEntity<?> Clientes() {
		return ResponseEntity.ok().body(cliServ.getClientes());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> Cliente(@PathVariable("id") Integer id) {
		Cliente categoria = cliServ.getCliente(id);
		return ResponseEntity.ok().body(categoria);
	}

}
