package com.streammuse.application.biblioteca;

import com.streammuse.domain.conta.Playlist;
import com.streammuse.domain.conta.RepositorioUsuario;
import com.streammuse.domain.conta.Usuario;
import com.streammuse.domain.shared.Cpf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicoPlaylist {

    private final RepositorioUsuario repositorioUsuario;

    public ServicoPlaylist(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Transactional
    public Playlist criar(CriarPlaylistComando comando) {
        Usuario usuario = buscarUsuario(comando.cpf());
        Playlist playlist = usuario.criarPlaylist(comando.nome());
        repositorioUsuario.salvar(usuario);
        return playlist;
    }

    @Transactional
    public Usuario adicionarMusica(AdicionarMusicaPlaylistComando comando) {
        Usuario usuario = buscarUsuario(comando.cpf());
        usuario.adicionarMusicaNaPlaylist(comando.nomePlaylist(), comando.musicaId(), comando.titulo(), comando.artista());
        return repositorioUsuario.salvar(usuario);
    }

    private Usuario buscarUsuario(String cpf) {
        return repositorioUsuario.buscarPorCpf(new Cpf(cpf))
                .orElseThrow(() -> new IllegalArgumentException("usuario-nao-encontrado"));
    }
}
