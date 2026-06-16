package com.streammuse.infrastructure.web.dto;

public record ResultadoOperacaoResponse(
        String linha,
        String operacao,
        boolean sucesso,
        String mensagem,
        Object detalhe
) {
}
