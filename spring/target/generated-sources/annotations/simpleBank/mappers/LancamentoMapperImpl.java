package simpleBank.mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import simpleBank.dto.LancamentoDTO;
import simpleBank.model.Conta;
import simpleBank.model.Lancamento;
import simpleBank.model.OperacaoLancamento;
import simpleBank.model.TipoLancamento;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-01T16:17:13-0300",
    comments = "version: 1.6.0.Beta1, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class LancamentoMapperImpl implements LancamentoMapper {

    @Override
    public LancamentoDTO toDTO(Lancamento lancamento) {
        if ( lancamento == null ) {
            return null;
        }

        LancamentoDTO lancamentoDTO = new LancamentoDTO();

        lancamentoDTO.setContaId( lancamentoContaId( lancamento ) );
        if ( lancamento.getData() != null ) {
            lancamentoDTO.setData( new SimpleDateFormat( "dd/MM/yyyy" ).format( lancamento.getData() ) );
        }
        if ( lancamento.getOperacao() != null ) {
            lancamentoDTO.setOperacao( lancamento.getOperacao().name() );
        }
        if ( lancamento.getTipo() != null ) {
            lancamentoDTO.setTipo( lancamento.getTipo().name() );
        }
        lancamentoDTO.setValor( lancamento.getValor() );

        return lancamentoDTO;
    }

    @Override
    public Lancamento toEntity(LancamentoDTO lancamentoDTO) {
        if ( lancamentoDTO == null ) {
            return null;
        }

        Lancamento lancamento = new Lancamento();

        try {
            if ( lancamentoDTO.getData() != null ) {
                lancamento.setData( new SimpleDateFormat().parse( lancamentoDTO.getData() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        if ( lancamentoDTO.getOperacao() != null ) {
            lancamento.setOperacao( Enum.valueOf( OperacaoLancamento.class, lancamentoDTO.getOperacao() ) );
        }
        if ( lancamentoDTO.getTipo() != null ) {
            lancamento.setTipo( Enum.valueOf( TipoLancamento.class, lancamentoDTO.getTipo() ) );
        }
        lancamento.setValor( lancamentoDTO.getValor() );

        return lancamento;
    }

    private Long lancamentoContaId(Lancamento lancamento) {
        Conta conta = lancamento.getConta();
        if ( conta == null ) {
            return null;
        }
        return conta.getId();
    }
}
