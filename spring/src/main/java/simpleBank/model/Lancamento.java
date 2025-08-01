package simpleBank.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double valor;

    @Enumerated(EnumType.STRING)
    private TipoLancamento tipo;

    @Enumerated(EnumType.STRING)
    private OperacaoLancamento operacao;

    @Column(nullable = false)
    private Date data;

    @JsonIgnoreProperties("lancamentos")
    @ManyToOne
    @JoinColumn(name = "conta")
    private Conta conta;
}
