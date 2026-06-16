package com.streammuse.domain.antifraude.regras;

import com.streammuse.domain.antifraude.ContextoAntiFraude;
import com.streammuse.domain.antifraude.RegraAntiFraude;
import com.streammuse.domain.antifraude.ResultadoAntiFraude;

public class RegraCartaoAtivo implements RegraAntiFraude {

    @Override
    public String codigoViolacao() {
        return "cartao-nao-ativo";
    }

    @Override
    public ResultadoAntiFraude avaliar(ContextoAntiFraude contexto) {
        if (!contexto.dadosUsuario().possuiCartaoAtivo()) {
            return ResultadoAntiFraude.recusada(codigoViolacao());
        }
        return ResultadoAntiFraude.aprovada();
    }
}
