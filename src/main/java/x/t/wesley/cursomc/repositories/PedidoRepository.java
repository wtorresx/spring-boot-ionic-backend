package x.t.wesley.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import x.t.wesley.cursomc.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	@Query("SELECT pedido FROM Pedido pedido WHERE pedido.id=:id")
	Pedido findPedido(@Param("id") Integer id);
	
}
