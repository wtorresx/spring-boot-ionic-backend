package x.t.wesley.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Estado;
import x.t.wesley.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estRep;

	public void postEstado(Estado estado) {
		estRep.save(estado);
	}

	public void postEstados(List<Estado> estados) {
		estRep.saveAll(estados);
	}
}
