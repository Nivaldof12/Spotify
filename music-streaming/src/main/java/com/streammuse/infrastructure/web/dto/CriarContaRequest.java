package com.streammuse.infrastructure.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CriarContaRequest(
        @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
        @NotBlank @Size(max = 120) String nome,
        @NotBlank @Email String email,
        @NotBlank String numeroCartao,
        boolean cartaoValido,
        boolean cartaoAtivo
) {
}
