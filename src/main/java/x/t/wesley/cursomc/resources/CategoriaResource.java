package x.t.wesley.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import x.t.wesley.cursomc.domain.Categoria;
import x.t.wesley.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService catServ;

	@GetMapping
	public ResponseEntity<?> Categorias() {
		return ResponseEntity.ok().body(catServ.getCategorias());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> Categoria(@PathVariable("id") Integer id) {
		Categoria categoria = catServ.getCategoria(id);
		return ResponseEntity.ok().body(categoria);
	}

}
