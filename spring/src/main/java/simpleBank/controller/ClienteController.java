package simpleBank.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import simpleBank.dto.ClienteDTO;
import simpleBank.service.logic.ClienteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cliente/")
public class ClienteController {

    @Autowired
    private final ClienteService clienteService;

    @GetMapping("/")
    public ResponseEntity<List<ClienteDTO>> getClientes() {
        List<ClienteDTO> clientesDTO = this.clienteService.getClientes();
        return ResponseEntity.status(HttpStatus.OK).body(clientesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable(value = "id") Long id) {
        ClienteDTO clienteDTO = this.clienteService.getClienteById(id);
        if (clienteDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(clienteDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // @PostMapping("/createCliente")
    // public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO clienteDTO) {
    //     ClienteDTO createdClienteDTO = this.clienteService.createCliente(clienteDTO);
    //     if (createdClienteDTO != null) {
    //         return ResponseEntity.ok().body(createdClienteDTO);
    //     } else {
    //         return ResponseEntity.badRequest().body(null);
    //     }
    // }

    @PutMapping("/")
    public ResponseEntity<ClienteDTO> updateCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO updatedClienteDTO = this.clienteService.updateCliente(clienteDTO);
        if (updatedClienteDTO != null) {
            return ResponseEntity.ok().body(updatedClienteDTO);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteDTO> removeCliente(@PathVariable(value = "id") Long id) {
        ClienteDTO removedClienteDTO = this.clienteService.removeCliente(id);
        if (removedClienteDTO != null) {
            return ResponseEntity.ok().body(removedClienteDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
