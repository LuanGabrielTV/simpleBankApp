package simpleBank.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import simpleBank.repository.ClienteRepository;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {
    private final ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clienteRepository.findUserByCpf(username);
    }
}
