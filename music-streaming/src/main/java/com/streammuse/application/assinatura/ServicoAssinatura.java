package com.streammuse.application.assinatura;

import com.streammuse.domain.antifraude.ResultadoAntiFraude;
import com.streammuse.domain.antifraude.ServicoAntiFraude;
import com.streammuse.domain.antifraude.SolicitacaoAutorizacaoTransacao;
import com.streammuse.domain.conta.RepositorioUsuario;
import com.streammuse.domain.conta.TipoPlanoAssinatura;
import com.streammuse.domain.conta.Usuario;
import com.streammuse.domain.shared.Cpf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ServicoAssinatura {

    private final RepositorioUsuario repositorioUsuario;
    private final ServicoAntiFraude servicoAntiFraude;

    public ServicoAssinatura(RepositorioUsuario repositorioUsuario, ServicoAntiFraude servicoAntiFraude) {
        this.repositorioUsuario = repositorioUsuario;
        this.servicoAntiFraude = servicoAntiFraude;
    }

    @Transactional
    public Usuario assinar(AssinarPlanoComando comando) {
        Cpf cpf = new Cpf(comando.cpf());
        Usuario usuario = repositorioUsuario.buscarPorCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("usuario-nao-encontrado"));

        SolicitacaoAutorizacaoTransacao solicitacao = new SolicitacaoAutorizacaoTransacao(
                cpf,
                valorDoPlano(comando.plano()),
                "StreamMuse-" + comando.plano().name()
        );

        ResultadoAntiFraude resultado = servicoAntiFraude.avaliarAssinatura(solicitacao);
        if (!resultado.aprovada()) {
            throw new IllegalStateException(resultado.violacao());
        }

        usuario.ativarAssinatura(comando.plano());
        return repositorioUsuario.salvar(usuario);
    }

    private BigDecimal valorDoPlano(TipoPlanoAssinatura plano) {
        return switch (plano) {
            case BASICO -> new BigDecimal("19.90");
            case PREMIUM -> new BigDecimal("29.90");
            case FAMILIA -> new BigDecimal("49.90");
        };
    }
}
