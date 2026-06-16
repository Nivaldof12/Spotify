package com.streammuse.infrastructure.config;

import com.streammuse.domain.antifraude.FornecedorDadosUsuario;
import com.streammuse.domain.antifraude.FornecedorHistoricoTransacoes;
import com.streammuse.domain.antifraude.RegraAntiFraude;
import com.streammuse.domain.antifraude.ServicoAntiFraude;
import com.streammuse.domain.antifraude.regras.RegraAltaFrequencia;
import com.streammuse.domain.antifraude.regras.RegraCartaoAtivo;
import com.streammuse.domain.antifraude.regras.RegraCartaoValido;
import com.streammuse.domain.antifraude.regras.RegraPlanoUnicoAtivo;
import com.streammuse.domain.antifraude.regras.RegraTransacaoDuplicada;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConfiguracaoAntiFraude {

    @Bean
    public ServicoAntiFraude servicoAntiFraude(
            FornecedorDadosUsuario fornecedorDadosUsuario,
            FornecedorHistoricoTransacoes fornecedorHistoricoTransacoes
    ) {
        List<RegraAntiFraude> regrasTransacao = List.of(
                new RegraCartaoValido(),
                new RegraCartaoAtivo(),
                new RegraAltaFrequencia(),
                new RegraTransacaoDuplicada()
        );

        List<RegraAntiFraude> regrasAssinatura = List.of(
                new RegraPlanoUnicoAtivo(),
                new RegraCartaoValido()
        );

        return new ServicoAntiFraude(
                fornecedorDadosUsuario,
                fornecedorHistoricoTransacoes,
                regrasTransacao,
                regrasAssinatura
        );
    }
}
