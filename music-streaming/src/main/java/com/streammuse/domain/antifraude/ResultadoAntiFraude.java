package com.streammuse.domain.antifraude;

public record ResultadoAntiFraude(
        boolean aprovada,
        String violacao
) {

    public static ResultadoAntiFraude aprovada() {
        return new ResultadoAntiFraude(true, null);
    }

    public static ResultadoAntiFraude recusada(String violacao) {
        return new ResultadoAntiFraude(false, violacao);
    }
}
