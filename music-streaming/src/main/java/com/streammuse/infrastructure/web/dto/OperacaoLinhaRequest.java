package com.streammuse.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

public record OperacaoLinhaRequest(
        @NotBlank String linha
) {
}
