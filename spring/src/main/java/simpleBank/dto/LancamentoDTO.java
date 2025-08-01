package simpleBank.dto;

import java.util.Date;

import lombok.Data;

@Data
public class LancamentoDTO {
    private Double valor;
    private String tipo;
    private Long contaId;
    private String data;
    private String operacao;
}
