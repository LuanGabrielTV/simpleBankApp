package simpleBank.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import simpleBank.dto.LancamentoDTO;
import simpleBank.model.Lancamento;


@Mapper(componentModel = "spring")
public interface LancamentoMapper {
    
    @Mapping(target = "contaId", source = "conta.id")
    @Mapping(target = "data", source = "lancamento.data", dateFormat = "dd/MM/yyyy")
    LancamentoDTO toDTO(Lancamento lancamento);

    @Mapping(target = "conta", ignore = true)
    @Mapping(target = "id", ignore = true)
    Lancamento toEntity(LancamentoDTO lancamentoDTO);

}
