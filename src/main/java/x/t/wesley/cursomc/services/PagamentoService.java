package x.t.wesley.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.t.wesley.cursomc.domain.Pagamento;
import x.t.wesley.cursomc.repositories.PagamentoRepository;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository pagRep;

	public void postPagamento(Pagamento pagamento) {
		pagRep.save(pagamento);
	}

	public void postPagamentos(List<Pagamento> pagamentos) {
		pagRep.saveAll(pagamentos);
	}

}
