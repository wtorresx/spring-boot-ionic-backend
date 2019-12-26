package x.t.wesley.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
import x.t.wesley.cursomc.domain.enums.TipoCliente;
import x.t.wesley.cursomc.services.CategoriaService;
import x.t.wesley.cursomc.services.CidadeService;
import x.t.wesley.cursomc.services.ClienteService;
import x.t.wesley.cursomc.services.EnderecoService;
import x.t.wesley.cursomc.services.EstadoService;
import x.t.wesley.cursomc.services.ItemPedidoService;
import x.t.wesley.cursomc.services.PagamentoService;
import x.t.wesley.cursomc.services.PedidoService;
import x.t.wesley.cursomc.services.ProdutoService;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

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

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");


		Produto p1 = new Produto(null, "Computado", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		catServ.postCategorias(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		prodServ.postProdutos(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		Cidade c4 = new Cidade(null, "Cotia", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3, c4));

		estServ.postEstados(Arrays.asList(est1, est2));
		cidServ.postCidades(Arrays.asList(c1, c2, c3, c4));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "85614781609", TipoCliente.PESSOAFISICA);

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", c1, cli1);
		e1.getTelefones().addAll(Arrays.asList("(34) 9999-9999"));

		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", c2, cli1);
		e2.getTelefones().addAll(Arrays.asList("(11) 9999-9999"));

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2019 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2019 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		cliServ.postClientes(Arrays.asList(cli1));
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
