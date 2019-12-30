package x.t.wesley.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import x.t.wesley.cursomc.domain.enums.TipoCliente;
import x.t.wesley.cursomc.dto.ClienteNewDTO;
import x.t.wesley.cursomc.repositories.ClienteRepository;
import x.t.wesley.cursomc.resources.exceptions.FieldMessage;
import x.t.wesley.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository cliRep;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext context) {
		List<FieldMessage> errors = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na errorsa

		if (clienteNewDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())
				&& !BR.isValidCPF(clienteNewDTO.getCpfOuCnpj())) {
			errors.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
		}

		if (clienteNewDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())
				&& !BR.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())) {
			errors.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
		}

		if (cliRep.findByEmail(clienteNewDTO.getEmail()) != null) {
			errors.add(new FieldMessage("email", "Email já cadastrado"));
		}
		for (FieldMessage e : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return errors.isEmpty();
	}
}