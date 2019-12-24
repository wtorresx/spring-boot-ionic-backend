package x.t.wesley.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Categoria;
import x.t.wesley.cursomc.repositories.CategoriaRepository;
import x.t.wesley.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository catRep;

	public List<Categoria> getCategorias() {
		return catRep.findAll();
	}

	public Categoria getCategoria(Integer id) {
		Optional<Categoria> categoria = catRep.findById(id);
		
		if(categoria.orElse(null)==null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ", Tipo: " + Categoria.class.getName());
		};
		
		return categoria.orElse(null);
	}

	public Categoria postCategoria(Categoria categoria) {
		//Garantir que será um novo registro!
		categoria.setId(null);
		
		return catRep.save(categoria);
	}

	public List<Categoria> postCategorias(List<Categoria> categorias) {
		return catRep.saveAll(categorias);
	}

}
