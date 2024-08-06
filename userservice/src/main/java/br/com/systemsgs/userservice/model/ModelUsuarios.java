package br.com.systemsgs.userservice.model;

import br.com.systemsgs.userservice.enums.TipoCarteira;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_usuarios")
@SequenceGenerator(name = "id_gen_usuarios", sequenceName = "usuarios_seq", initialValue = 1, allocationSize = 1)
public class ModelUsuarios implements Serializable {

    @Id
    @GeneratedValue(generator = "id_gen_usuarios", strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome_usuario", length = 150)
    private String nome;

    @Column(name = "email", unique = true, length = 150)
    private String email;

    @Column(name = "cpf", unique = true, length = 20)
    private String cpf;

    @Column(name = "valor_na_conta")
    private BigDecimal valorConta = BigDecimal.ZERO;

    @Column(name = "tipo_carteira")
    @Enumerated(EnumType.STRING)
    private TipoCarteira tipoCarteira;

    @Column(name = "quantidade_transacoes_realizadas")
    private Integer quantidadeTransacoesRealizadas = 0;

    @Column(name = "total_transacoes_realizadas")
    private BigDecimal totalTransacoesRealizadas = BigDecimal.ZERO;

    @Column(name = "data_criacao_usuario")
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_alteracao")
    @CreationTimestamp
    private LocalDateTime dataAlteracao;

    public void debitar(BigDecimal valor) {
        this.valorConta = this.valorConta.subtract(valor);
    }

    public void creditar(BigDecimal valor) {
        this.valorConta = this.valorConta.add(valor);
    }

    public void acrescentaTransacoesRealizadas(){
        this.quantidadeTransacoesRealizadas = this.quantidadeTransacoesRealizadas + 1;
    }

    public void multiplicaTotalTransacoesRealizadas(BigDecimal valorTransferencia){
        this.totalTransacoesRealizadas = this.totalTransacoesRealizadas.multiply(valorTransferencia);
    }
}
