package com.streammuse.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record FavoritarMusicaRequest(
        @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
        @NotBlank String musicaId,
        @NotBlank String titulo,
        @NotBlank String artista
) {
}
