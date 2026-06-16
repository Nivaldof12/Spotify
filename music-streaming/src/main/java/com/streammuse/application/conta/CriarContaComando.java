package com.streammuse.application.conta;

public record CriarContaComando(
        String cpf,
        String nome,
        String email,
        String numeroCartao,
        boolean cartaoValido,
        boolean cartaoAtivo
) {
}
