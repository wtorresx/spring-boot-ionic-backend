package x.t.wesley.cursomc.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.ItemPedido;
import x.t.wesley.cursomc.domain.PagamentoComBoleto;
import x.t.wesley.cursomc.domain.Pedido;
import x.t.wesley.cursomc.domain.enums.EstadoPagamento;
import x.t.wesley.cursomc.repositories.ItemPedidoRepository;
import x.t.wesley.cursomc.repositories.PagamentoRepository;
import x.t.wesley.cursomc.repositories.PedidoRepository;
import x.t.wesley.cursomc.repositories.ProdutoRepository;
import x.t.wesley.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedRep;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagRep;

	@Autowired
	private ProdutoRepository prodRep;

	@Autowired
	private ItemPedidoRepository ipRep;

	public List<Pedido> getPedidos() {
		return pedRep.findAll();
	}

	public Pedido getPedido(Integer id) {
		Optional<Pedido> pedido = pedRep.findById(id);

		if (pedido.orElse(null) == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName());
		}

		return pedido.orElse(null);
	}

	public Pedido postPedido(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
		}

		pedido = pedRep.save(pedido);
		pagRep.save(pedido.getPagamento());

		for (ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(prodRep.findProduto(ip.getProduto().getId()).getPreço());
			ip.setPedido(pedido);

			ipRep.save(ip);
			
		}
				
		return pedido;

	}

	public void postPedidos(List<Pedido> pedidos) {
		pedRep.saveAll(pedidos);
	}

}
