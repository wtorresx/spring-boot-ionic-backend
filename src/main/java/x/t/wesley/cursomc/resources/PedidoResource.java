package x.t.wesley.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> Pedido(@PathVariable("id") Integer id) {
		Pedido pedido = pedServ.getPedido(id);
		return ResponseEntity.ok().body(pedido);
	}

}
