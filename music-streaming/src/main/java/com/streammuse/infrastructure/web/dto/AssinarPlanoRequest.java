package com.streammuse.infrastructure.web.dto;

import com.streammuse.domain.conta.TipoPlanoAssinatura;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AssinarPlanoRequest(
        @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
        @NotNull TipoPlanoAssinatura plano
) {
}
