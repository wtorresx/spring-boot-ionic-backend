package x.t.wesley.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Categoria;
import x.t.wesley.cursomc.domain.Cidade;
import x.t.wesley.cursomc.domain.Cliente;
import x.t.wesley.cursomc.domain.Endereco;
import x.t.wesley.cursomc.domain.Estado;
import x.t.wesley.cursomc.domain.ItemPedido;
import x.t.wesley.cursomc.domain.Pagamento;
import x.t.wesley.cursomc.domain.PagamentoComBoleto;
import x.t.wesley.cursomc.domain.PagamentoComCartao;
import x.t.wesley.cursomc.domain.Pedido;
import x.t.wesley.cursomc.domain.Produto;
import x.t.wesley.cursomc.domain.enums.EstadoPagamento;
import x.t.wesley.cursomc.domain.enums.Perfil;
import x.t.wesley.cursomc.domain.enums.TipoCliente;

@Service
public class DBService {

	@Autowired
	private CategoriaService catServ;

	@Autowired
	private ProdutoService prodServ;

	@Autowired
	private CidadeService cidServ;

	@Autowired
	private EstadoService estServ;

	@Autowired
	private ClienteService cliServ;

	@Autowired
	private EnderecoService endServ;

	@Autowired
	private PedidoService pedServ;

	@Autowired
	private PagamentoService pagServ;

	@Autowired
	private ItemPedidoService ipServ;
	
	@Autowired
	private BCryptPasswordEncoder passEncoder;

	public void instantiateTestDatabase() throws ParseException {

		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));

		catServ.postCategorias(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		prodServ.postProdutos(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		Estado est1 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Cotia", est1);
		Cidade c2 = new Cidade(null, "Itapevi", est1);

		est1.getCidades().addAll(Arrays.asList(c1, c2));

		estServ.postEstados(Arrays.asList(est1));
		cidServ.postCidades(Arrays.asList(c1, c2));

		Cliente cli1 = new Cliente(null, "Cristina Torres Xavier", "cristina.torres@outlook.com.br", "23496321059",
				TipoCliente.PESSOAFISICA, passEncoder.encode("123"));
		Cliente cli2 = new Cliente(null, "Wesley Torres Xavier", "wesley.xavier@outlook.com", "39202215081",
				TipoCliente.PESSOAFISICA, passEncoder.encode("123"));
		
		cli2.addPerfil(Perfil.ADMIN);
		
		Endereco e1 = new Endereco(null, "Avenida em Cotia", "Sem Numero", "Sem Sala", "No Bairro", "00000000", c1, cli1);
		e1.getTelefones().addAll(Arrays.asList("(11) 7777-7777", "(11) 977777777"));
		
		Endereco e2 = new Endereco(null, "Avenida em Itapevi", "Sem Numero", "Sem Sala", "No Bairro", "00000000", c2, cli2);
		e2.getTelefones().addAll(Arrays.asList("(11) 6666-6666", "(11) 966666666"));

		cli1.getEnderecos().addAll(Arrays.asList(e1));
		cli2.getEnderecos().addAll(Arrays.asList(e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2019 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2019 19:35"), cli2, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		cliServ.postClientes(Arrays.asList(cli1, cli2));
		endServ.postEnderecos(Arrays.asList(e1, e2));
		
		pedServ.postPedidos(Arrays.asList(ped1, ped2));
		pagServ.postPagamentos(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		ipServ.postItemPedidos(Arrays.asList(ip1, ip2, ip3));
	}
}
