package x.t.wesley.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import x.t.wesley.cursomc.domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	
	@Query("SELECT categoria FROM Categoria categoria WHERE categoria.id=:id")
	Categoria findCategoria(@Param("id") Integer id);
}
