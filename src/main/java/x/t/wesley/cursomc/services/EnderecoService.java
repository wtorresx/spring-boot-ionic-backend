package x.t.wesley.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Endereco;
import x.t.wesley.cursomc.repositories.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository endRep;

	public void postEndereco(Endereco endereco) {
		endRep.save(endereco);
	}

	public void postEnderecos(List<Endereco> enderecos) {
		endRep.saveAll(enderecos);
	}

}
