package com.streammuse.domain.antifraude.regras;

import com.streammuse.domain.antifraude.ContextoAntiFraude;
import com.streammuse.domain.antifraude.RegraAntiFraude;
import com.streammuse.domain.antifraude.ResultadoAntiFraude;

public class RegraAltaFrequencia implements RegraAntiFraude {

    private static final int LIMITE_TRANSACOES = 3;

    @Override
    public String codigoViolacao() {
        return "alta-frequencia-pequeno-intervalo";
    }

    @Override
    public ResultadoAntiFraude avaliar(ContextoAntiFraude contexto) {
        long total = contexto.transacoesRecentes().size();
        if (total >= LIMITE_TRANSACOES) {
            return ResultadoAntiFraude.recusada(codigoViolacao());
        }
        return ResultadoAntiFraude.aprovada();
    }
}
