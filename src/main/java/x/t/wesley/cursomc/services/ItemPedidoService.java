package x.t.wesley.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.ItemPedido;
import x.t.wesley.cursomc.repositories.ItemPedidoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository ipRep;

	public void postItemPedido(ItemPedido itemPedido) {
		ipRep.save(itemPedido);
	}

	public void postItemPedidos(List<ItemPedido> itemPedido) {
		ipRep.saveAll(itemPedido);
	}

}
