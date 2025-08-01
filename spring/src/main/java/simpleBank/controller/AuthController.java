package simpleBank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import simpleBank.dto.ClienteDTO;
import simpleBank.dto.LoginRequestDTO;
import simpleBank.dto.LoginResponseDTO;
import simpleBank.model.Cliente;
import simpleBank.service.logic.ClienteService;
import simpleBank.service.logic.TokenService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private final ClienteService clienteService;

    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO createdClienteDTO = this.clienteService.createCliente(clienteDTO);
        if (createdClienteDTO != null) {
            return ResponseEntity.ok().body(createdClienteDTO);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.getCpf(), loginRequestDTO.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Cliente) auth.getPrincipal());
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
    }


    
}
