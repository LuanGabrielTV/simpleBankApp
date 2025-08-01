package simpleBank.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import simpleBank.dto.LancamentoDTO;
import simpleBank.dto.TransferenciaDTO;
import simpleBank.service.logic.LancamentoService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/lancamento/")
public class LancamentoController {

    @Autowired
    private final LancamentoService lancamentoService;

    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<LancamentoDTO>> getLancamentosByContaId(@PathVariable(value = "contaId") Long contaId) {
        List<LancamentoDTO> lancamentosDTO = this.lancamentoService.getLancamentosByContaId(contaId);
        return ResponseEntity.status(HttpStatus.OK).body(lancamentosDTO);
    }

    @GetMapping("/saldo/{clienteId}")
    public ResponseEntity<Double> getSaldoByClienteId(@PathVariable(value = "clienteId") Long clienteId) {
        Double saldo = this.lancamentoService.getSaldoByClienteId(clienteId);
        return ResponseEntity.status(HttpStatus.OK).body(saldo);
    }

    @PostMapping("/")
    public ResponseEntity<LancamentoDTO> createLancamento(@RequestBody LancamentoDTO lancamentoDTO) {
        LancamentoDTO createdLancamentoDTO = this.lancamentoService.createLancamento(lancamentoDTO);
        if (createdLancamentoDTO != null) {
            return ResponseEntity.ok().body(createdLancamentoDTO);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/transferir")
    public ResponseEntity<LancamentoDTO> createTransferencia(@RequestBody TransferenciaDTO transferenciaDTO) {
        LancamentoDTO createdTransferenciaDTO = this.lancamentoService.createTransferencia(transferenciaDTO);
        if (createdTransferenciaDTO != null) {
            return ResponseEntity.ok().body(createdTransferenciaDTO);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
