package simpleBank.dto;

import lombok.Data;

@Data
public class TransferenciaDTO {
    private Long origemId;
    private Long destinoId;
    private Double valor;
}
