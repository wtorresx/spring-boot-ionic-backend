package x.t.wesley.cursomc.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import x.t.wesley.cursomc.domain.Cliente;
import x.t.wesley.cursomc.domain.ItemPedido;
import x.t.wesley.cursomc.domain.PagamentoComBoleto;
import x.t.wesley.cursomc.domain.Pedido;
import x.t.wesley.cursomc.domain.enums.EstadoPagamento;
import x.t.wesley.cursomc.repositories.ClienteRepository;
import x.t.wesley.cursomc.repositories.ItemPedidoRepository;
import x.t.wesley.cursomc.repositories.PagamentoRepository;
import x.t.wesley.cursomc.repositories.PedidoRepository;
import x.t.wesley.cursomc.repositories.ProdutoRepository;
import x.t.wesley.cursomc.security.UserSS;
import x.t.wesley.cursomc.services.exceptions.AuthorizationException;
import x.t.wesley.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedRep;

	@Autowired
	private ClienteRepository cliRep;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagRep;

	@Autowired
	private ProdutoRepository prodRep;

	@Autowired
	private ItemPedidoRepository ipRep;

	@Autowired
	private EmailService emailService;

	public List<Pedido> getPedidos() {
		return pedRep.findAll();
	}

	public Page<Pedido> getPedidosPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		UserSS user = UserService.authenticated();
		
		if(user==null) {
			throw new AuthorizationException("Acesso Negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = cliRep.findCliente(user.getId());		
		return pedRep.findByCliente(cliente, pageRequest);
	}

	public Pedido getPedido(Integer id) {
		Pedido pedido = pedRep.findPedido(id);

		if (pedido == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName());
		}

		return pedido;
	}

	@Transactional
	public Pedido postPedido(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(cliRep.findCliente(pedido.getCliente().getId()));
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
			ip.setProduto(prodRep.findProduto(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreço());
			ip.setPedido(pedido);

			ipRep.save(ip);

		}

		emailService.sendOrderConfirmationHtmlEmail(pedido);

		return pedido;

	}

	public void postPedidos(List<Pedido> pedidos) {
		pedRep.saveAll(pedidos);
	}

}
