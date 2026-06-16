package com.streammuse.domain.antifraude;

public record DadosUsuarioAntiFraude(
        boolean possuiCartaoValido,
        boolean possuiCartaoAtivo,
        boolean possuiPlanoAtivo
) {
}
