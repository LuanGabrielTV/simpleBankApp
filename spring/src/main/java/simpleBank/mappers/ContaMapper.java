package simpleBank.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import simpleBank.dto.ContaDTO;
import simpleBank.model.Conta;

@Mapper(componentModel = "spring")
public interface ContaMapper {

    @Mapping(target = "cpf", source = "cliente.cpf")
    ContaDTO toDTO(Conta conta);

    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "lancamentos", ignore = true)
    Conta toEntity(ContaDTO conta);

}
