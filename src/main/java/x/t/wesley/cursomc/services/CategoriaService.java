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

	public void postCategoria(Categoria categoria) {
		catRep.save(categoria);
	}

	public void postCategorias(List<Categoria> categorias) {
		catRep.saveAll(categorias);
	}

}
