package com.streammuse.domain.antifraude;

import com.streammuse.domain.antifraude.regras.RegraAltaFrequencia;
import com.streammuse.domain.antifraude.regras.RegraCartaoAtivo;
import com.streammuse.domain.antifraude.regras.RegraCartaoValido;
import com.streammuse.domain.antifraude.regras.RegraTransacaoDuplicada;
import com.streammuse.domain.shared.Cpf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServicoAntiFraudeTest {

    private ServicoAntiFraude servicoAntiFraude;
    private DadosUsuarioAntiFraude dadosUsuario;
    private List<TransacaoAntiFraude> historico;
    private Cpf cpf;

    @BeforeEach
    void setUp() {
        cpf = new Cpf("12345678901");
        dadosUsuario = new DadosUsuarioAntiFraude(true, true, false);
        historico = new ArrayList<>();

        servicoAntiFraude = new ServicoAntiFraude(
                c -> Optional.of(dadosUsuario),
                (c, desde) -> historico,
                List.of(
                        new RegraCartaoValido(),
                        new RegraCartaoAtivo(),
                        new RegraAltaFrequencia(),
                        new RegraTransacaoDuplicada()
                ),
                List.of()
        );
    }

    @Test
    void deveRecusarQuandoCartaoNaoEstaAtivo() {
        dadosUsuario = new DadosUsuarioAntiFraude(true, false, false);
        servicoAntiFraude = new ServicoAntiFraude(
                c -> Optional.of(dadosUsuario),
                (c, desde) -> historico,
                List.of(new RegraCartaoValido(), new RegraCartaoAtivo()),
                List.of()
        );

        ResultadoAntiFraude resultado = servicoAntiFraude.avaliarTransacao(
                new SolicitacaoAutorizacaoTransacao(cpf, new BigDecimal("10.00"), "Loja")
        );

        assertFalse(resultado.aprovada());
        assertEquals("cartao-nao-ativo", resultado.violacao());
    }

    @Test
    void deveRecusarQuandoHaAltaFrequencia() {
        historico.add(new TransacaoAntiFraude(new BigDecimal("1.00"), "A", Instant.now()));
        historico.add(new TransacaoAntiFraude(new BigDecimal("2.00"), "B", Instant.now()));
        historico.add(new TransacaoAntiFraude(new BigDecimal("3.00"), "C", Instant.now()));

        ResultadoAntiFraude resultado = servicoAntiFraude.avaliarTransacao(
                new SolicitacaoAutorizacaoTransacao(cpf, new BigDecimal("10.00"), "Loja")
        );

        assertFalse(resultado.aprovada());
        assertEquals("alta-frequencia-pequeno-intervalo", resultado.violacao());
    }

    @Test
    void deveRecusarTransacaoDuplicada() {
        historico.add(new TransacaoAntiFraude(new BigDecimal("10.00"), "Loja", Instant.now()));
        historico.add(new TransacaoAntiFraude(new BigDecimal("10.00"), "Loja", Instant.now()));

        ResultadoAntiFraude resultado = servicoAntiFraude.avaliarTransacao(
                new SolicitacaoAutorizacaoTransacao(cpf, new BigDecimal("10.00"), "Loja")
        );

        assertFalse(resultado.aprovada());
        assertEquals("transacao-duplicada", resultado.violacao());
    }

    @Test
    void deveAprovarTransacaoValida() {
        ResultadoAntiFraude resultado = servicoAntiFraude.avaliarTransacao(
                new SolicitacaoAutorizacaoTransacao(cpf, new BigDecimal("10.00"), "Loja")
        );

        assertTrue(resultado.aprovada());
    }
}
