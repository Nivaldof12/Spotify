package com.streammuse.domain.antifraude.regras;

import com.streammuse.domain.antifraude.ContextoAntiFraude;
import com.streammuse.domain.antifraude.RegraAntiFraude;
import com.streammuse.domain.antifraude.ResultadoAntiFraude;

public class RegraCartaoValido implements RegraAntiFraude {

    @Override
    public String codigoViolacao() {
        return "cartao-invalido";
    }

    @Override
    public ResultadoAntiFraude avaliar(ContextoAntiFraude contexto) {
        if (!contexto.dadosUsuario().possuiCartaoValido()) {
            return ResultadoAntiFraude.recusada(codigoViolacao());
        }
        return ResultadoAntiFraude.aprovada();
    }
}
