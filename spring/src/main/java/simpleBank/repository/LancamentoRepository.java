package simpleBank.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import simpleBank.model.Lancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findByContaId(@Param("contaId") Long contaId);

    @Query(value = "SELECT ((SELECT COALESCE(SUM(valor), 0) as credito from lancamento where conta = :contaId AND tipo = 'CREDITO') - (SELECT COALESCE(SUM(valor), 0) as debito from lancamento where conta = :contaId AND tipo = 'DEBITO'))", nativeQuery = true)
    Double getSaldoByContaId(@Param("contaId") Long contaId);

    @Query(value = "SELECT ((SELECT COALESCE(SUM(valor), 0) as credito from lancamento as l inner join conta as c on l.conta = c.id where c.cliente = :clienteId AND tipo = 'CREDITO') - (SELECT COALESCE(SUM(valor), 0) as debito from lancamento as l inner join conta as c on l.conta = c.id where c.cliente = :clienteId AND tipo = 'DEBITO'));", nativeQuery = true)
    Double getSaldoByClienteId(@Param("clienteId") Long clienteId);
}
