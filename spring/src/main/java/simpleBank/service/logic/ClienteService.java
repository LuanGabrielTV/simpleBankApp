package simpleBank.service.logic;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import simpleBank.dto.ClienteDTO;
import simpleBank.mappers.ClienteMapper;
import simpleBank.model.Cliente;
import simpleBank.repository.ClienteRepository;

@RequiredArgsConstructor
@Service
public class ClienteService {

    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private final ClienteMapper clienteMapper;

    public List<ClienteDTO> getClientes() {
        return this.clienteRepository.findAll().stream().map(clienteMapper::toDTO).toList();
    }

    public Long getUserIdByCpf(String cpf) {
        return clienteRepository.findUserIdByCpf(cpf).orElseThrow();
    }

    public ClienteDTO getClienteById(Long id) {
        Optional<Cliente> cliente = this.clienteRepository.findById(id);
        if (cliente.isPresent()) {
            return clienteMapper.toDTO(cliente.get());
        } else {
            return null;
        }
    }

    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        if (clienteRepository.findByCpf(clienteDTO.getCpf()).isPresent() || clienteDTO.getId() != null) {
            return null;
        }

        String encryptedPassword = new BCryptPasswordEncoder()
                .encode(clienteDTO.getPassword());

        Cliente cliente = new Cliente();
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setNome(clienteDTO.getNome());
        cliente.setPassword(encryptedPassword);
        cliente.setTelefone(clienteDTO.getTelefone());
        Cliente createdCliente = clienteRepository.save(cliente);
        return clienteMapper.toDTO(createdCliente);
    }

    public ClienteDTO updateCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.getId() == null) {
            return null;
        } else {
            Optional<Cliente> cliente = this.clienteRepository.findById(clienteDTO.getId());
            if (cliente.isPresent()) {
                cliente.get().setCpf(clienteDTO.getCpf());
                cliente.get().setNome(clienteDTO.getNome());
                cliente.get().setTelefone(clienteDTO.getTelefone());
                Cliente createdCliente = this.clienteRepository.save(cliente.get());
                return clienteMapper.toDTO(createdCliente);
            } else {
                return null;
            }
        }
    }

    public ClienteDTO removeCliente(Long id) {
        Optional<Cliente> cliente = this.clienteRepository.findById(id);

        if (cliente.isPresent()) {
            this.clienteRepository.delete(cliente.get());
            return clienteMapper.toDTO(cliente.get());
        } else {
            return null;
        }
    }

}
