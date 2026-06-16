package com.streammuse.infrastructure.web.dto;

import com.streammuse.domain.pagamento.Transacao;

public record TransacaoResponse(
        Long id,
        String cpf,
        String valor,
        String comerciante,
        String status,
        String motivoRecusa
) {

    public static TransacaoResponse de(Transacao transacao) {
        return new TransacaoResponse(
                transacao.getId(),
                transacao.getCpfUsuario().getValor(),
                transacao.getValor().toPlainString(),
                transacao.getComerciante(),
                transacao.getStatus().name(),
                transacao.getMotivoRecusa()
        );
    }
}
