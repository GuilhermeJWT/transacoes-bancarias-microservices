package br.com.systemsgs.userservice.dto;

import br.com.systemsgs.userservice.enums.TipoCarteira;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public record ModelUsuariosDTO

        (

        @NotBlank(message = "Nome Obrigatório!")
        String nome,

        @NotBlank(message = "Email Obrigatório!")
        @Email(message = "Formato de E-mail Inválido!")
        String email,

        @CPF(message = "Formato de Cpf Inválido!")
        @NotBlank(message = "CPF Obrigatório")
        String cpf,

        @NotNull(message = "Informe o Valor.")
        @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que 0.")
        @DecimalMax(value = "999999.0", message = "Valor muito alto para a conta.")
        @JsonProperty("valor_conta")
        BigDecimal valorConta,

        @NotNull(message = "Informe o tipo de Carteira - USUARIO_COMUM ou LOJISTA")
        @Enumerated(EnumType.STRING)
        @JsonProperty("tipo_carteira")
        TipoCarteira tipoCarteira

        ){}
