package com.streammuse.application.operacao;

import com.streammuse.application.assinatura.AssinarPlanoComando;
import com.streammuse.application.assinatura.ServicoAssinatura;
import com.streammuse.application.biblioteca.AdicionarMusicaPlaylistComando;
import com.streammuse.application.biblioteca.CriarPlaylistComando;
import com.streammuse.application.biblioteca.FavoritarMusicaComando;
import com.streammuse.application.biblioteca.ServicoFavoritarMusica;
import com.streammuse.application.biblioteca.ServicoPlaylist;
import com.streammuse.application.conta.CriarContaComando;
import com.streammuse.application.conta.ServicoCriacaoConta;
import com.streammuse.application.pagamento.AutorizarTransacaoComando;
import com.streammuse.application.pagamento.ServicoAutorizacaoTransacao;
import com.streammuse.domain.conta.TipoPlanoAssinatura;
import com.streammuse.infrastructure.web.dto.ResultadoOperacaoResponse;
import com.streammuse.infrastructure.web.dto.TransacaoResponse;
import com.streammuse.infrastructure.web.dto.UsuarioResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Orquestrador de operações por linha de texto.
 * Formato:
 * C <cpf> <nome> <email> <numeroCartao> <cartaoValido> <cartaoAtivo>
 * T <cpf> <valor> <comerciante>
 * F <cpf> <musicaId> <titulo> <artista>
 * P CRIAR <cpf> <nomePlaylist>
 * P ADICIONAR <cpf> <nomePlaylist> <musicaId> <titulo> <artista>
 * A <cpf> <plano>
 */
@Service
public class ProcessadorOperacaoLinha {

    private final ServicoCriacaoConta servicoCriacaoConta;
    private final ServicoAutorizacaoTransacao servicoAutorizacaoTransacao;
    private final ServicoFavoritarMusica servicoFavoritarMusica;
    private final ServicoPlaylist servicoPlaylist;
    private final ServicoAssinatura servicoAssinatura;

    public ProcessadorOperacaoLinha(
            ServicoCriacaoConta servicoCriacaoConta,
            ServicoAutorizacaoTransacao servicoAutorizacaoTransacao,
            ServicoFavoritarMusica servicoFavoritarMusica,
            ServicoPlaylist servicoPlaylist,
            ServicoAssinatura servicoAssinatura
    ) {
        this.servicoCriacaoConta = servicoCriacaoConta;
        this.servicoAutorizacaoTransacao = servicoAutorizacaoTransacao;
        this.servicoFavoritarMusica = servicoFavoritarMusica;
        this.servicoPlaylist = servicoPlaylist;
        this.servicoAssinatura = servicoAssinatura;
    }

    public ResultadoOperacaoResponse processar(String linha) {
        String linhaNormalizada = linha.trim();
        if (linhaNormalizada.isEmpty()) {
            return new ResultadoOperacaoResponse(linha, "DESCONHECIDA", false, "linha-vazia", null);
        }

        String[] partes = linhaNormalizada.split("\\s+");
        String tipo = partes[0].toUpperCase();

        try {
            return switch (tipo) {
                case "C" -> processarCriacaoConta(linhaNormalizada, partes);
                case "T" -> processarTransacao(linhaNormalizada, partes);
                case "F" -> processarFavorito(linhaNormalizada, partes);
                case "P" -> processarPlaylist(linhaNormalizada, partes);
                case "A" -> processarAssinatura(linhaNormalizada, partes);
                default -> new ResultadoOperacaoResponse(linhaNormalizada, "DESCONHECIDA", false, "operacao-invalida", null);
            };
        } catch (RuntimeException ex) {
            return new ResultadoOperacaoResponse(linhaNormalizada, tipo, false, ex.getMessage(), null);
        }
    }

    private ResultadoOperacaoResponse processarCriacaoConta(String linha, String[] partes) {
        validarTamanho(partes, 7, linha);
        var comando = new CriarContaComando(
                partes[1],
                partes[2],
                partes[3],
                partes[4],
                Boolean.parseBoolean(partes[5]),
                Boolean.parseBoolean(partes[6])
        );
        UsuarioResponse usuario = UsuarioResponse.de(servicoCriacaoConta.criarConta(comando));
        return new ResultadoOperacaoResponse(linha, "CRIACAO_CONTA", true, "conta-criada", usuario);
    }

    private ResultadoOperacaoResponse processarTransacao(String linha, String[] partes) {
        validarTamanho(partes, 4, linha);
        var comando = new AutorizarTransacaoComando(partes[1], new BigDecimal(partes[2]), partes[3]);
        TransacaoResponse transacao = TransacaoResponse.de(servicoAutorizacaoTransacao.autorizar(comando));
        boolean sucesso = "AUTORIZADA".equals(transacao.status());
        return new ResultadoOperacaoResponse(
                linha,
                "AUTORIZACAO_TRANSACAO",
                sucesso,
                sucesso ? "transacao-autorizada" : transacao.motivoRecusa(),
                transacao
        );
    }

    private ResultadoOperacaoResponse processarFavorito(String linha, String[] partes) {
        validarTamanho(partes, 5, linha);
        var comando = new FavoritarMusicaComando(partes[1], partes[2], partes[3], juntar(partes, 4));
        UsuarioResponse usuario = UsuarioResponse.de(servicoFavoritarMusica.favoritar(comando));
        return new ResultadoOperacaoResponse(linha, "FAVORITAR_MUSICA", true, "musica-favoritada", usuario);
    }

    private ResultadoOperacaoResponse processarPlaylist(String linha, String[] partes) {
        String acao = partes[1].toUpperCase();
        if ("CRIAR".equals(acao)) {
            validarTamanho(partes, 4, linha);
            var comando = new CriarPlaylistComando(partes[2], juntar(partes, 3));
            return new ResultadoOperacaoResponse(
                    linha,
                    "PLAYLIST",
                    true,
                    "playlist-criada",
                    servicoPlaylist.criar(comando).getNome()
            );
        }
        if ("ADICIONAR".equals(acao)) {
            validarTamanho(partes, 7, linha);
            var comando = new AdicionarMusicaPlaylistComando(
                    partes[2], partes[3], partes[4], partes[5], juntar(partes, 6)
            );
            UsuarioResponse usuario = UsuarioResponse.de(servicoPlaylist.adicionarMusica(comando));
            return new ResultadoOperacaoResponse(linha, "PLAYLIST", true, "musica-adicionada", usuario);
        }
        throw new IllegalArgumentException("acao-playlist-invalida");
    }

    private ResultadoOperacaoResponse processarAssinatura(String linha, String[] partes) {
        validarTamanho(partes, 3, linha);
        var comando = new AssinarPlanoComando(partes[1], TipoPlanoAssinatura.valueOf(partes[2].toUpperCase()));
        UsuarioResponse usuario = UsuarioResponse.de(servicoAssinatura.assinar(comando));
        return new ResultadoOperacaoResponse(linha, "ASSINATURA", true, "assinatura-ativada", usuario);
    }

    private void validarTamanho(String[] partes, int minimo, String linha) {
        if (partes.length < minimo) {
            throw new IllegalArgumentException("formato-linha-invalido: " + linha);
        }
    }

    private String juntar(String[] partes, int inicio) {
        return String.join(" ", Arrays.copyOfRange(partes, inicio, partes.length));
    }
}
