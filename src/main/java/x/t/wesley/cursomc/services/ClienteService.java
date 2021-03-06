package x.t.wesley.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import x.t.wesley.cursomc.domain.Cidade;
import x.t.wesley.cursomc.domain.Cliente;
import x.t.wesley.cursomc.domain.Endereco;
import x.t.wesley.cursomc.domain.enums.Perfil;
import x.t.wesley.cursomc.domain.enums.TipoCliente;
import x.t.wesley.cursomc.dto.ClienteDTO;
import x.t.wesley.cursomc.dto.ClienteNewDTO;
import x.t.wesley.cursomc.repositories.ClienteRepository;
import x.t.wesley.cursomc.repositories.EnderecoRepository;
import x.t.wesley.cursomc.security.UserSS;
import x.t.wesley.cursomc.services.exceptions.AuthorizationException;
import x.t.wesley.cursomc.services.exceptions.DataIntegrityException;
import x.t.wesley.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cliRep;

	@Autowired
	private EnderecoRepository endRep;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.client.profile.prefix}")
	private String prefix;

	@Value("${img.client.profile.size}")
	private int size;

	public List<Cliente> getClientes() {
		return cliRep.findAll();
	}

	public Page<Cliente> getClientesPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return cliRep.findAll(pageRequest);
	}

	public Cliente getCliente(Integer id) {

		UserSS user = UserService.authenticated();

		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		Cliente cliente = cliRep.findCliente(id);

		if (cliente == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
		}

		return cliente;
	}

	@Transactional
	public Cliente postCliente(Cliente cliente) {
		// Garantir que será um novo registro!
		cliente.setId(null);
		endRep.saveAll(cliente.getEnderecos());
		return cliRep.save(cliente);
	}

	public List<Cliente> postClientes(List<Cliente> clientes) {
		return cliRep.saveAll(clientes);
	}

	public Cliente putCliente(Cliente cliente) {

		// Garantir que a cliente existe
		Cliente newCliente = getCliente(cliente.getId());

		updateData(newCliente, cliente);
		return cliRep.save(newCliente);
	}

	public void deleteCliente(Integer id) {
		getCliente(id);

		try {
			cliRep.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas!");
		}
	}

	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
	}

	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {

		Cliente cli = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(clienteNewDTO.getTipo()), passEncoder.encode(clienteNewDTO.getSenha()));
		Cidade cid = new Cidade(clienteNewDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(),
				clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cid, cli);

		cli.getEnderecos().add(end);

		for (String x : clienteNewDTO.getTelefones()) {
			end.getTelefones().add(x);
		}

		return cli;
	}

	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {

		UserSS user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);

		jpgImage = imageService.cropSquare(jpgImage);

		jpgImage = imageService.resize(jpgImage, size);

		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

	}

}
