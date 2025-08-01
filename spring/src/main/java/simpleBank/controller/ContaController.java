package simpleBank.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import simpleBank.dto.ContaDTO;
import simpleBank.service.logic.ContaService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/conta/")
public class ContaController {

    @Autowired
    private final ContaService contaService;

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ContaDTO>> getContasByClienteId(@PathVariable(value = "clienteId") Long clienteId) {
        List<ContaDTO> contasDTO = this.contaService.getContasByClienteId(clienteId);
        return ResponseEntity.status(HttpStatus.OK).body(contasDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<ContaDTO>> getContas() {
        List<ContaDTO> contasDTO = this.contaService.getContas();
        return ResponseEntity.status(HttpStatus.OK).body(contasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> getContaById(@PathVariable(value = "id") Long id) {
        ContaDTO contaDTO = this.contaService.getContaById(id);
        if (contaDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(contaDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ContaDTO> createConta(@RequestBody ContaDTO contaDTO) {
        ContaDTO createdContaDTO = this.contaService.createConta(contaDTO);
        if (createdContaDTO != null) {
            return ResponseEntity.ok().body(createdContaDTO);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/")
    public ResponseEntity<ContaDTO> updateConta(@RequestBody ContaDTO contaDTO) {
        ContaDTO updatedContaDTO = this.contaService.updateConta(contaDTO);
        if (updatedContaDTO != null) {
            return ResponseEntity.ok().body(updatedContaDTO);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
