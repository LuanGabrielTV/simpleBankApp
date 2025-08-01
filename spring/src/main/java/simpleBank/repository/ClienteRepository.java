package simpleBank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import simpleBank.model.Cliente;

@Repository 
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);

    UserDetails findUserByCpf(String cpf);

    @Query(value="SELECT id FROM cliente WHERE cpf = :cpf", nativeQuery = true)
    Optional<Long> findUserIdByCpf(@Param("cpf") String cpf);

}