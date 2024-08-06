package br.com.systemsgs.transaction_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tb_transacoes")
public class ModelTransacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @MongoId
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_transacao")
    private UUID idTransacao;

    @Column(name = "id_pagador")
    private Long idPagador;

    @Column(name = "nome_pagador")
    private String nomePagador;

    @Column(name = "id_beneficiario")
    private Long idBeneficiario;

    @Column(name = "nome_beneficiario")
    private String nomeBeneficiario;

    @Column(name = "email_beneficiario")
    private String emailBeneficiario;

    @Field(targetType = FieldType.DECIMAL128)
    @Column(name = "valor_transferencia")
    private BigDecimal valorTransferencia;

    @Column(name = "tipo_carteira")
    private String tipoCarteira;

}
