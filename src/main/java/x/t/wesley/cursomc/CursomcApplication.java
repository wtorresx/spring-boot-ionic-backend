package x.t.wesley.cursomc;

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
import x.t.wesley.cursomc.domain.Produto;
import x.t.wesley.cursomc.domain.enums.TipoCliente;
import x.t.wesley.cursomc.services.CategoriaService;
import x.t.wesley.cursomc.services.CidadeService;
import x.t.wesley.cursomc.services.ClienteService;
import x.t.wesley.cursomc.services.EnderecoService;
import x.t.wesley.cursomc.services.EstadoService;
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

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computado", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		catServ.postCategorias(Arrays.asList(cat1, cat2));
		prodServ.postProdutos(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estServ.postEstados(Arrays.asList(est1, est2));
		cidServ.postCidades(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "85614781609", TipoCliente.PESSOAFISICA);

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", c1, cli1);
		e1.getTelefones().addAll(Arrays.asList("(34) 9999-9999"));

		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", c2, cli1);
		e2.getTelefones().addAll(Arrays.asList("(11) 9999-9999"));

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		cliServ.postClientes(Arrays.asList(cli1));
		endServ.postEnderecos(Arrays.asList(e1, e2));
		
	}
}
