package simpleBank.service.logic;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import simpleBank.dto.ContaDTO;
import simpleBank.mappers.ContaMapper;
import simpleBank.model.Cliente;
import simpleBank.model.Conta;
import simpleBank.repository.ClienteRepository;
import simpleBank.repository.ContaRepository;

@RequiredArgsConstructor
@Service
public class ContaService {

    @Autowired
    private final ContaRepository contaRepository;

    @Autowired
    private final ContaMapper contaMapper;

    @Autowired
    private final ClienteRepository clienteRepository;

    public List<ContaDTO> getContas() {
        List<Conta> contas = this.contaRepository.findAll();
        List<ContaDTO> contasDTO = contas.stream().map(contaMapper::toDTO).toList();
        return contasDTO;
    }

    public List<ContaDTO> getContasByClienteId(Long id) {
        return this.contaRepository.findByClienteId(id);
    }

    public ContaDTO getContaById(Long id) {
        Optional<Conta> conta = this.contaRepository.findById(id);
        if (conta.isPresent()) {
            return contaMapper.toDTO(conta.get());
        } else {
            return null;
        }
    }

    public ContaDTO updateConta(ContaDTO contaDTO) {
        if (contaDTO.getId() != null) {
            Optional<Conta> contaOptional = contaRepository.findById(contaDTO.getId());

            if (contaOptional.isEmpty()) {
                return null;
            }
            
            Conta conta = contaOptional.get();

            if (contaDTO.getLimite() <= conta.getLimite()) {
                return null;
            }

            conta.setLimite(contaDTO.getLimite());

            Conta updatedConta = this.contaRepository.save(conta);
            return contaMapper.toDTO(updatedConta);

        } else {
            return null;
        }
    }

    public ContaDTO createConta(ContaDTO contaDTO) {
        if (contaDTO.getId() == null) {
            Conta conta = contaMapper.toEntity(contaDTO);
            conta.setLimite(0.0);
            Optional<Cliente> titular = clienteRepository.findByCpf(contaDTO.getCpf());
            if (titular.isEmpty()) {
                return null;
            }
            conta.setCliente(titular.get());

            String numero;
            List<String> numerosJaCadastrados = this.contaRepository.findNumerosByClienteId(titular.get().getId());
            Random rand = new Random();

            do {
                numero = titular.get().getNome().substring(0, 2).toUpperCase();
                numero += (int) (rand.nextDouble() * 1000000);
            } while (numerosJaCadastrados.contains(numero));

            conta.setNumero(numero);
            Conta createdConta = this.contaRepository.save(conta);
            return contaMapper.toDTO(createdConta);
        } else {
            return null;
        }
    }
}
