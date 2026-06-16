package com.streammuse.domain.antifraude.regras;

import com.streammuse.domain.antifraude.ContextoAntiFraude;
import com.streammuse.domain.antifraude.RegraAntiFraude;
import com.streammuse.domain.antifraude.ResultadoAntiFraude;

public class RegraPlanoUnicoAtivo implements RegraAntiFraude {

    @Override
    public String codigoViolacao() {
        return "plano-ativo-existente";
    }

    @Override
    public ResultadoAntiFraude avaliar(ContextoAntiFraude contexto) {
        if (contexto.dadosUsuario().possuiPlanoAtivo()) {
            return ResultadoAntiFraude.recusada(codigoViolacao());
        }
        return ResultadoAntiFraude.aprovada();
    }
}
