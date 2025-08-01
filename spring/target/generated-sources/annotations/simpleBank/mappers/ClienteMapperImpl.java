package simpleBank.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import simpleBank.dto.ClienteDTO;
import simpleBank.model.Cliente;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-01T16:17:13-0300",
    comments = "version: 1.6.0.Beta1, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public ClienteDTO toDTO(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setCpf( cliente.getCpf() );
        clienteDTO.setId( cliente.getId() );
        clienteDTO.setNome( cliente.getNome() );
        clienteDTO.setPassword( cliente.getPassword() );
        clienteDTO.setTelefone( cliente.getTelefone() );

        return clienteDTO;
    }

    @Override
    public Cliente toEntity(ClienteDTO cliente) {
        if ( cliente == null ) {
            return null;
        }

        Cliente cliente1 = new Cliente();

        cliente1.setCpf( cliente.getCpf() );
        cliente1.setId( cliente.getId() );
        cliente1.setNome( cliente.getNome() );
        cliente1.setTelefone( cliente.getTelefone() );

        return cliente1;
    }
}
