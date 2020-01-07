package x.t.wesley.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import x.t.wesley.cursomc.domain.Pedido;
import x.t.wesley.cursomc.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService pedServ;

	@GetMapping
	public ResponseEntity<?> Pedidos() {
		return ResponseEntity.ok().body(pedServ.getPedidos());
	}
	
	@GetMapping(value="/page")
	public ResponseEntity<Page<Pedido>> PedidosPage(
			@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue = "instante") String orderBy,
			@RequestParam(value="direction", defaultValue = "DESC") String direction) {
		
		Page<Pedido> pedidos = pedServ.getPedidosPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(pedidos);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Pedido> Pedido(@PathVariable("id") Integer id) {
		Pedido pedido = pedServ.getPedido(id);
		return ResponseEntity.ok().body(pedido);
	}
	
	@PostMapping
	public ResponseEntity<Void> novaCategoria(@Valid @RequestBody Pedido pedido) {
			
		pedido = pedServ.postPedido(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}


}
