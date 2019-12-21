package x.t.wesley.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Pedido;
import x.t.wesley.cursomc.repositories.PedidoRepository;
import x.t.wesley.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedRep;
	
	public List<Pedido> getPedidos() {
		return pedRep.findAll();
	}

	public Pedido getPedido(Integer id) {
		Optional<Pedido> pedido = pedRep.findById(id);
		
		if(pedido.orElse(null)==null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ", Tipo: " + Pedido.class.getName());
		};
		
		return pedido.orElse(null);
	}

	public void postPedido(Pedido pedido) {
		pedRep.save(pedido);
	}

	public void postPedidos(List<Pedido> pedidos) {
		pedRep.saveAll(pedidos);
	}
	
	

}
