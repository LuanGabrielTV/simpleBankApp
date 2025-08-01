package simpleBank.service.logic;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import simpleBank.dto.LancamentoDTO;
import simpleBank.dto.TransferenciaDTO;
import simpleBank.mappers.LancamentoMapper;
import simpleBank.model.Conta;
import simpleBank.model.Lancamento;
import simpleBank.model.OperacaoLancamento;
import simpleBank.model.TipoLancamento;
import simpleBank.repository.ContaRepository;
import simpleBank.repository.LancamentoRepository;

@RequiredArgsConstructor
@Service
public class LancamentoService {

    @Autowired
    private final LancamentoRepository lancamentoRepository;

    @Autowired
    private final ContaRepository contaRepository;

    @Autowired
    private final LancamentoMapper lancamentoMapper;

    public List<LancamentoDTO> getLancamentosByContaId(Long id) {
        List<Lancamento> lancamentos = this.lancamentoRepository.findByContaId(id);
        return lancamentos.stream().map(lancamentoMapper::toDTO).toList();
    }

    public LancamentoDTO createLancamento(LancamentoDTO lancamentoDTO) {
        if (lancamentoDTO.getContaId() != null && lancamentoDTO.getValor() > 0
                && TipoLancamento.contains(lancamentoDTO.getTipo())) {

            Optional<Conta> contaOptional = this.contaRepository.findById(lancamentoDTO.getContaId());

            if (contaOptional.isEmpty()) {
                return null;
            }

            Conta conta = contaOptional.get();

            Double saldo = this.lancamentoRepository.getSaldoByContaId(lancamentoDTO.getContaId());
            if (lancamentoDTO.getTipo().equals("DEBITO") && lancamentoDTO.getValor() > (saldo + conta.getLimite())) {
                return null;
            }

            Lancamento lancamento = lancamentoMapper.toEntity(lancamentoDTO);
            lancamento.setData(new Date());
            lancamento.setConta(conta);

            if (lancamentoDTO.getTipo().equals("CREDITO")) {
                lancamento.setOperacao(OperacaoLancamento.DEPOSITO);
                Double saldoTodasAsContas = this.lancamentoRepository
                        .getSaldoByClienteId(lancamento.getConta().getCliente().getId());
                if (lancamentoDTO.getValor() > saldoTodasAsContas) {
                    // aumenta em 10% o valor do depósito
                    Lancamento bonus = new Lancamento();
                    bonus.setConta(conta);
                    bonus.setData(lancamento.getData());
                    bonus.setTipo(TipoLancamento.CREDITO);
                    bonus.setOperacao(OperacaoLancamento.BONUS);
                    bonus.setValor(lancamentoDTO.getValor() * 0.1);
                    this.lancamentoRepository.save(bonus);
                }
            } else {
                lancamento.setOperacao(OperacaoLancamento.SAQUE);
            }

            Lancamento createdLancamento = this.lancamentoRepository.save(lancamento);
            return lancamentoMapper.toDTO(createdLancamento);
        } else {
            return null;
        }
    }

    public Double getSaldoByClienteId(Long id) {
        return lancamentoRepository.getSaldoByClienteId(id);
    }

    public LancamentoDTO createTransferencia(TransferenciaDTO transferenciaDTO) {
        if (transferenciaDTO.getDestinoId() != null && transferenciaDTO.getOrigemId() != null
                && transferenciaDTO.getValor() > 0) {

            Optional<Conta> contaOrigemOptional = this.contaRepository.findById(transferenciaDTO.getOrigemId());
            Optional<Conta> contaDestinoOptional = this.contaRepository.findById(transferenciaDTO.getDestinoId());

            if (contaOrigemOptional.isEmpty() || contaDestinoOptional.isEmpty()) {
                return null;
            }

            Conta contaOrigem = contaOrigemOptional.get();
            Conta contaDestino = contaDestinoOptional.get();

            if (contaOrigem.getId() == contaDestino.getId()) {
                // não pode transferir para a conta de origem
                return null;
            }

            Double saldo = this.lancamentoRepository.getSaldoByContaId(transferenciaDTO.getOrigemId());

            // System.out.println(transferenciaDTO.getValor());
            // System.out.println(saldo);

            if (transferenciaDTO.getValor() > saldo + contaOrigem.getLimite()) {
                // checa se há saldo para fazer tal transferência
                return null;
            }

            if (contaOrigem.getCliente().getId() != contaDestino.getCliente().getId()) {
                // checa se são clientes diferentes
                if (transferenciaDTO.getValor() * 1.1 > saldo + contaOrigem.getLimite()) {
                    // checa se há saldo para fazer tal transferência
                    return null;
                }
            }

            Lancamento lancamentoDebito = new Lancamento();
            lancamentoDebito.setConta(contaOrigem);
            lancamentoDebito.setData(new Date());
            lancamentoDebito.setOperacao(OperacaoLancamento.TRANSFERENCIA);
            lancamentoDebito.setTipo(TipoLancamento.DEBITO);
            lancamentoDebito.setValor(transferenciaDTO.getValor());
            Lancamento createdLancamentoDebito = this.lancamentoRepository.save(lancamentoDebito);

            Lancamento lancamentoCredito = new Lancamento();
            lancamentoCredito.setConta(contaDestino);
            lancamentoCredito.setData(new Date());
            lancamentoCredito.setOperacao(OperacaoLancamento.TRANSFERENCIA);
            lancamentoCredito.setTipo(TipoLancamento.CREDITO);
            lancamentoCredito.setValor(transferenciaDTO.getValor());
            Lancamento createdLancamentoCredito = this.lancamentoRepository.save(lancamentoCredito);

            if (contaOrigem.getCliente().getId() != contaDestino.getCliente().getId()) {
                // se são clientes diferentes, adicionar taxa
                Lancamento lancamentoTaxa = new Lancamento();
                lancamentoTaxa.setConta(contaOrigem);
                lancamentoTaxa.setData(new Date());
                lancamentoTaxa.setOperacao(OperacaoLancamento.TAXA);
                lancamentoTaxa.setTipo(TipoLancamento.DEBITO);
                lancamentoTaxa.setValor(transferenciaDTO.getValor()*0.1);
                Lancamento createdLancamentoTaxa = this.lancamentoRepository.save(lancamentoTaxa);
            }

            return lancamentoMapper.toDTO(createdLancamentoDebito);

        } else {
            return null;
        }
    }

}
