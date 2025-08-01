package simpleBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContaDTO {
    private Long id;
    private String numero;
    private String cpf;
    private Double saldo;
    private Double limite;
}
