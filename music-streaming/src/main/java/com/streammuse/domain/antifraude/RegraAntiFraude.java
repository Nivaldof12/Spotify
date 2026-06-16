package com.streammuse.domain.antifraude;

public interface RegraAntiFraude {

    String codigoViolacao();

    ResultadoAntiFraude avaliar(ContextoAntiFraude contexto);
}
