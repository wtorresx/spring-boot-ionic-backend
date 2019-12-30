package x.t.wesley.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Categoria;
import x.t.wesley.cursomc.dto.CategoriaDTO;
import x.t.wesley.cursomc.repositories.CategoriaRepository;
import x.t.wesley.cursomc.services.exceptions.DataIntegrityException;
import x.t.wesley.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository catRep;

	public List<Categoria> getCategorias() {
		return catRep.findAll();
	}

	public Page<Categoria> getCategoriasPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return catRep.findAll(pageRequest);
	}

	public Categoria getCategoria(Integer id) {
		Categoria categoria = catRep.findCategoria(id);

		if (categoria == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName());
		}

		return categoria;
	}

	public Categoria postCategoria(Categoria categoria) {
		// Garantir que será um novo registro!
		categoria.setId(null);
		return catRep.save(categoria);
	}

	public List<Categoria> postCategorias(List<Categoria> categorias) {
		return catRep.saveAll(categorias);
	}

	public Categoria putCategoria(Categoria categoria) {

		// Garantir que a categoria existe
		Categoria newCategoria = getCategoria(categoria.getId());

		updateData(newCategoria, categoria);
		return catRep.save(newCategoria);
	}

	public void deleteCategoria(Integer id) {
		getCategoria(id);

		try {
			catRep.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos!");
		}
	}

	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}

	private void updateData(Categoria newCategoria, Categoria categoria) {
		newCategoria.setNome(categoria.getNome());
	}
}
