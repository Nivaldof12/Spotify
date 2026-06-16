package com.streammuse.application.biblioteca;

import com.streammuse.domain.conta.RepositorioUsuario;
import com.streammuse.domain.conta.Usuario;
import com.streammuse.domain.shared.Cpf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicoFavoritarMusica {

    private final RepositorioUsuario repositorioUsuario;

    public ServicoFavoritarMusica(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Transactional
    public Usuario favoritar(FavoritarMusicaComando comando) {
        Usuario usuario = buscarUsuario(comando.cpf());
        usuario.favoritarMusica(comando.musicaId(), comando.titulo(), comando.artista());
        return repositorioUsuario.salvar(usuario);
    }

    private Usuario buscarUsuario(String cpf) {
        return repositorioUsuario.buscarPorCpf(new Cpf(cpf))
                .orElseThrow(() -> new IllegalArgumentException("usuario-nao-encontrado"));
    }
}
