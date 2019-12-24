package x.t.wesley.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import x.t.wesley.cursomc.domain.Categoria;
import x.t.wesley.cursomc.dto.CategoriaDTO;
import x.t.wesley.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService catServ;

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> Categorias() {
		List<Categoria> categorias = catServ.getCategorias();
		
		List<CategoriaDTO> categoriasDTO = categorias.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(categoriasDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> Categoria(@PathVariable("id") Integer id) {
		Categoria categoria = catServ.getCategoria(id);
		return ResponseEntity.ok().body(categoria);
	}

	@PostMapping
	public ResponseEntity<Void> novaCategoria(@RequestBody Categoria categoria) {

		categoria = catServ.postCategoria(categoria);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> editarCategoria(@RequestBody Categoria categoria, @PathVariable Integer id) {
		categoria.setId(id);
		categoria = catServ.putCategoria(categoria);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletarCategoria(@PathVariable Integer id) {
		catServ.deleteCategoria(id);
		return ResponseEntity.noContent().build();
	}

}
