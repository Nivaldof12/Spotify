package com.streammuse.domain.antifraude;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Domain Service do bounded context Anti-Fraude.
 * Avalia solicitações aplicando regras extensíveis (Open/Closed).
 */
public class ServicoAntiFraude {

    private static final Duration INTERVALO_ANALISE = Duration.ofMinutes(2);

    private final FornecedorDadosUsuario fornecedorDadosUsuario;
    private final FornecedorHistoricoTransacoes fornecedorHistoricoTransacoes;
    private final List<RegraAntiFraude> regrasTransacao;
    private final List<RegraAntiFraude> regrasAssinatura;

    public ServicoAntiFraude(
            FornecedorDadosUsuario fornecedorDadosUsuario,
            FornecedorHistoricoTransacoes fornecedorHistoricoTransacoes,
            List<RegraAntiFraude> regrasTransacao,
            List<RegraAntiFraude> regrasAssinatura
    ) {
        this.fornecedorDadosUsuario = fornecedorDadosUsuario;
        this.fornecedorHistoricoTransacoes = fornecedorHistoricoTransacoes;
        this.regrasTransacao = List.copyOf(regrasTransacao);
        this.regrasAssinatura = List.copyOf(regrasAssinatura);
    }

    public ResultadoAntiFraude avaliarTransacao(SolicitacaoAutorizacaoTransacao solicitacao) {
        DadosUsuarioAntiFraude dadosUsuario = buscarDadosUsuario(solicitacao);
        Instant desde = Instant.now().minus(INTERVALO_ANALISE);
        List<TransacaoAntiFraude> transacoesRecentes =
                fornecedorHistoricoTransacoes.buscarTransacoesRecentes(solicitacao.cpf(), desde);

        ContextoAntiFraude contexto = new ContextoAntiFraude(solicitacao, dadosUsuario, transacoesRecentes);
        return aplicarRegras(regrasTransacao, contexto);
    }

    public ResultadoAntiFraude avaliarAssinatura(SolicitacaoAutorizacaoTransacao solicitacao) {
        DadosUsuarioAntiFraude dadosUsuario = buscarDadosUsuario(solicitacao);
        ContextoAntiFraude contexto = new ContextoAntiFraude(solicitacao, dadosUsuario, List.of());
        return aplicarRegras(regrasAssinatura, contexto);
    }

    private DadosUsuarioAntiFraude buscarDadosUsuario(SolicitacaoAutorizacaoTransacao solicitacao) {
        return fornecedorDadosUsuario
                .buscarDadosPorCpf(solicitacao.cpf())
                .orElseThrow(() -> new IllegalArgumentException("usuario-nao-encontrado"));
    }

    private ResultadoAntiFraude aplicarRegras(List<RegraAntiFraude> regras, ContextoAntiFraude contexto) {
        for (RegraAntiFraude regra : regras) {
            ResultadoAntiFraude resultado = regra.avaliar(contexto);
            if (!resultado.aprovada()) {
                return resultado;
            }
        }
        return ResultadoAntiFraude.aprovada();
    }
}
