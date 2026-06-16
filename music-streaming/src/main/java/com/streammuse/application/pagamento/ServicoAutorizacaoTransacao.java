package com.streammuse.application.pagamento;

import com.streammuse.domain.antifraude.ResultadoAntiFraude;
import com.streammuse.domain.antifraude.ServicoAntiFraude;
import com.streammuse.domain.antifraude.SolicitacaoAutorizacaoTransacao;
import com.streammuse.domain.pagamento.RepositorioTransacao;
import com.streammuse.domain.pagamento.Transacao;
import com.streammuse.domain.shared.Cpf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicoAutorizacaoTransacao {

    private final ServicoAntiFraude servicoAntiFraude;
    private final RepositorioTransacao repositorioTransacao;

    public ServicoAutorizacaoTransacao(
            ServicoAntiFraude servicoAntiFraude,
            RepositorioTransacao repositorioTransacao
    ) {
        this.servicoAntiFraude = servicoAntiFraude;
        this.repositorioTransacao = repositorioTransacao;
    }

    @Transactional
    public Transacao autorizar(AutorizarTransacaoComando comando) {
        Cpf cpf = new Cpf(comando.cpf());
        SolicitacaoAutorizacaoTransacao solicitacao = new SolicitacaoAutorizacaoTransacao(
                cpf,
                comando.valor(),
                comando.comerciante()
        );

        ResultadoAntiFraude resultado = servicoAntiFraude.avaliarTransacao(solicitacao);

        Transacao transacao = resultado.aprovada()
                ? Transacao.autorizar(cpf, comando.valor(), comando.comerciante())
                : Transacao.recusar(cpf, comando.valor(), comando.comerciante(), resultado.violacao());

        return repositorioTransacao.salvar(transacao);
    }
}
