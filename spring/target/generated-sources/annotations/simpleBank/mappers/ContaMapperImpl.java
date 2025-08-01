package simpleBank.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import simpleBank.dto.ContaDTO;
import simpleBank.model.Cliente;
import simpleBank.model.Conta;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-01T16:17:13-0300",
    comments = "version: 1.6.0.Beta1, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ContaMapperImpl implements ContaMapper {

    @Override
    public ContaDTO toDTO(Conta conta) {
        if ( conta == null ) {
            return null;
        }

        ContaDTO contaDTO = new ContaDTO();

        contaDTO.setCpf( contaClienteCpf( conta ) );
        contaDTO.setId( conta.getId() );
        contaDTO.setLimite( conta.getLimite() );
        contaDTO.setNumero( conta.getNumero() );

        return contaDTO;
    }

    @Override
    public Conta toEntity(ContaDTO conta) {
        if ( conta == null ) {
            return null;
        }

        Conta conta1 = new Conta();

        conta1.setId( conta.getId() );
        conta1.setLimite( conta.getLimite() );
        conta1.setNumero( conta.getNumero() );

        return conta1;
    }

    private String contaClienteCpf(Conta conta) {
        Cliente cliente = conta.getCliente();
        if ( cliente == null ) {
            return null;
        }
        return cliente.getCpf();
    }
}
