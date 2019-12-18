package x.t.wesley.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Pedido;
import x.t.wesley.cursomc.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedRep;

	public void postPedido(Pedido pedido) {
		pedRep.save(pedido);
	}

	public void postPedidos(List<Pedido> pedidos) {
		pedRep.saveAll(pedidos);
	}

}
