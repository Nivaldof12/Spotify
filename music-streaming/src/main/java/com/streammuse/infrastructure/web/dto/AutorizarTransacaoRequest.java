package com.streammuse.infrastructure.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record AutorizarTransacaoRequest(
        @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
        @NotNull @DecimalMin("0.01") BigDecimal valor,
        @NotBlank String comerciante
) {
}
