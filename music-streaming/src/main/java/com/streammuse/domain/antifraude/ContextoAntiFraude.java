package com.streammuse.domain.antifraude;

import com.streammuse.domain.shared.Cpf;

import java.math.BigDecimal;
import java.util.List;

public record ContextoAntiFraude(
        SolicitacaoAutorizacaoTransacao solicitacao,
        DadosUsuarioAntiFraude dadosUsuario,
        List<TransacaoAntiFraude> transacoesRecentes
) {

    public Cpf cpf() {
        return solicitacao.cpf();
    }

    public BigDecimal valor() {
        return solicitacao.valor();
    }

    public String comerciante() {
        return solicitacao.comerciante();
    }
}
