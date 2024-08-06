package br.com.systemsgs.transaction_service.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tb_transacoes")
public class ModelTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @MongoId
    @Field(name = "id_transacao")
    private UUID idTransacao;

    @Indexed(name = "id_pagador_index")
    @Field(name = "id_pagador")
    private Long idPagador;

    @Field(name = "nome_pagador")
    private String nomePagador;

    @Indexed(name = "id_beneficiario_index")
    @Field(name = "id_beneficiario")
    private Long idBeneficiario;

    @Field(name = "nome_beneficiario")
    private String nomeBeneficiario;

    @Field(name = "email_beneficiario")
    private String emailBeneficiario;

    @Field(name = "valor_transferencia",targetType = FieldType.DECIMAL128)
    private BigDecimal valorTransferencia;

    @Field(name = "tipo_carteira")
    private String tipoCarteira;

    @CreatedDate
    @Field(name = "data_transacao")
    private LocalDateTime dataTransacao = LocalDateTime.now();
}
