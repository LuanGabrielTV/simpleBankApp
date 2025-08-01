package simpleBank.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import simpleBank.dto.ContaDTO;
import simpleBank.model.Conta;

@Repository 
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Query(value="SELECT C.id, C.numero, CL.cpf, (SELECT ((SELECT COALESCE(SUM(valor), 0) as credito from lancamento where conta = C.id AND tipo = 'CREDITO') - (SELECT COALESCE(SUM(valor), 0) as debito from lancamento where conta = C.id AND tipo = 'DEBITO'))) as saldo, C.limite as limite from conta as C inner join cliente as CL on C.cliente = CL.id where CL.id = :clienteId", nativeQuery = true)
    List<ContaDTO> findByClienteId(@Param("clienteId") Long clienteId);

    @Query(value="SELECT numero from conta where cliente = :clienteId", nativeQuery = true)
    List<String> findNumerosByClienteId(@Param("clienteId") Long clienteId);

}