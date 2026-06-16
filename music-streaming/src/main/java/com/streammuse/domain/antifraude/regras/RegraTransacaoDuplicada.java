package com.streammuse.domain.antifraude.regras;

import com.streammuse.domain.antifraude.ContextoAntiFraude;
import com.streammuse.domain.antifraude.RegraAntiFraude;
import com.streammuse.domain.antifraude.ResultadoAntiFraude;
import com.streammuse.domain.antifraude.TransacaoAntiFraude;

public class RegraTransacaoDuplicada implements RegraAntiFraude {

    private static final int LIMITE_DUPLICADAS = 2;

    @Override
    public String codigoViolacao() {
        return "transacao-duplicada";
    }

    @Override
    public ResultadoAntiFraude avaliar(ContextoAntiFraude contexto) {
        long duplicadas = contexto.transacoesRecentes().stream()
                .filter(t -> t.valor().compareTo(contexto.valor()) == 0
                        && t.comerciante().equalsIgnoreCase(contexto.comerciante()))
                .count();

        if (duplicadas >= LIMITE_DUPLICADAS) {
            return ResultadoAntiFraude.recusada(codigoViolacao());
        }
        return ResultadoAntiFraude.aprovada();
    }
}
