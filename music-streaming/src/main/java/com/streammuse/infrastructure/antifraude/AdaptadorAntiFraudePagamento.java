package com.streammuse.infrastructure.antifraude;

import com.streammuse.domain.antifraude.FornecedorHistoricoTransacoes;
import com.streammuse.domain.antifraude.TransacaoAntiFraude;
import com.streammuse.domain.pagamento.RepositorioTransacao;
import com.streammuse.domain.pagamento.Transacao;
import com.streammuse.domain.shared.Cpf;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

/**
 * Anti-Corruption Layer: traduz transações do contexto Pagamento
 * para o modelo anti-fraude.
 */
@Component
public class AdaptadorAntiFraudePagamento implements FornecedorHistoricoTransacoes {

    private final RepositorioTransacao repositorioTransacao;

    public AdaptadorAntiFraudePagamento(RepositorioTransacao repositorioTransacao) {
        this.repositorioTransacao = repositorioTransacao;
    }

    @Override
    public List<TransacaoAntiFraude> buscarTransacoesRecentes(Cpf cpf, Instant desde) {
        return repositorioTransacao.buscarTransacoesAutorizadasRecentes(cpf, desde).stream()
                .map(this::traduzir)
                .toList();
    }

    private TransacaoAntiFraude traduzir(Transacao transacao) {
        return new TransacaoAntiFraude(
                transacao.getValor(),
                transacao.getComerciante(),
                transacao.getDataHora()
        );
    }
}
