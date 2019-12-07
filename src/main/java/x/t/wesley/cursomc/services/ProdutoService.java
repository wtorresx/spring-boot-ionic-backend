package x.t.wesley.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Produto;
import x.t.wesley.cursomc.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository prodRep;
	
	public void postProduto(Produto categoria) {
		prodRep.save(categoria);
	}

	public void postProdutos(List<Produto> produtos) {
		prodRep.saveAll(produtos);
	}

}
