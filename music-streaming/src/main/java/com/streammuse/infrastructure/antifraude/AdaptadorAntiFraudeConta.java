package com.streammuse.infrastructure.antifraude;

import com.streammuse.domain.antifraude.DadosUsuarioAntiFraude;
import com.streammuse.domain.antifraude.FornecedorDadosUsuario;
import com.streammuse.domain.antifraude.FornecedorHistoricoTransacoes;
import com.streammuse.domain.antifraude.TransacaoAntiFraude;
import com.streammuse.domain.conta.RepositorioUsuario;
import com.streammuse.domain.conta.Usuario;
import com.streammuse.domain.pagamento.RepositorioTransacao;
import com.streammuse.domain.pagamento.Transacao;
import com.streammuse.domain.shared.Cpf;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Anti-Corruption Layer: traduz dados dos contextos Conta e Pagamento
 * para o vocabulário do bounded context Anti-Fraude.
 */
@Component
public class AdaptadorAntiFraudeConta implements FornecedorDadosUsuario {

    private final RepositorioUsuario repositorioUsuario;

    public AdaptadorAntiFraudeConta(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Optional<DadosUsuarioAntiFraude> buscarDadosPorCpf(Cpf cpf) {
        return repositorioUsuario.buscarPorCpf(cpf)
                .map(this::traduzir);
    }

    private DadosUsuarioAntiFraude traduzir(Usuario usuario) {
        return new DadosUsuarioAntiFraude(
                usuario.possuiCartaoValido(),
                usuario.possuiCartaoAtivo(),
                usuario.possuiPlanoAtivo()
        );
    }
}
