package x.t.wesley.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Cidade;
import x.t.wesley.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidRep;

	public void postCidade(Cidade cidade) {
		cidRep.save(cidade);
	}

	public void postCidades(List<Cidade> cidades) {
		cidRep.saveAll(cidades);
	}

}
