package simpleBank.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import simpleBank.dto.ClienteDTO;
import simpleBank.model.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteDTO toDTO(Cliente cliente);

    @Mapping(target = "contas", ignore = true)
    @Mapping(target = "password", ignore = true)
    Cliente toEntity(ClienteDTO cliente);
}
