package x.t.wesley.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Categoria;
import x.t.wesley.cursomc.domain.Produto;
import x.t.wesley.cursomc.repositories.CategoriaRepository;
import x.t.wesley.cursomc.repositories.ProdutoRepository;
import x.t.wesley.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository prodRep;

	@Autowired
	private CategoriaRepository catRep;

	public List<Produto> getProdutos() {
		return prodRep.findAll();
	}

	public Produto getProduto(Integer id) {
		Produto produto = prodRep.findProduto(id);

		if (produto== null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName());
		}
		;

		return produto;
	}

	public Page<Produto> getProdutosPage(String nome, List<Integer> ids, Integer page, Integer linesPerPage,
			String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = catRep.findAllById(ids);
		
		return prodRep.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

	}

	public void postProduto(Produto categoria) {
		prodRep.save(categoria);
	}

	public void postProdutos(List<Produto> produtos) {
		prodRep.saveAll(produtos);
	}

}
