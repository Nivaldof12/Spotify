package com.streammuse.infrastructure.web;

import com.streammuse.api.biblioteca.BibliotecaMusicalApi;
import com.streammuse.application.biblioteca.AdicionarMusicaPlaylistComando;
import com.streammuse.application.biblioteca.CriarPlaylistComando;
import com.streammuse.application.biblioteca.FavoritarMusicaComando;
import com.streammuse.application.biblioteca.ServicoFavoritarMusica;
import com.streammuse.application.biblioteca.ServicoPlaylist;
import com.streammuse.infrastructure.web.dto.AdicionarMusicaPlaylistRequest;
import com.streammuse.infrastructure.web.dto.CriarPlaylistRequest;
import com.streammuse.infrastructure.web.dto.FavoritarMusicaRequest;
import com.streammuse.infrastructure.web.dto.PlaylistResponse;
import com.streammuse.infrastructure.web.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/biblioteca")
public class BibliotecaController implements BibliotecaMusicalApi {

    private final ServicoFavoritarMusica servicoFavoritarMusica;
    private final ServicoPlaylist servicoPlaylist;

    public BibliotecaController(ServicoFavoritarMusica servicoFavoritarMusica, ServicoPlaylist servicoPlaylist) {
        this.servicoFavoritarMusica = servicoFavoritarMusica;
        this.servicoPlaylist = servicoPlaylist;
    }

    @Override
    @PostMapping("/favoritas")
    public ResponseEntity<UsuarioResponse> favoritarMusica(@Valid @RequestBody FavoritarMusicaRequest request) {
        var comando = new FavoritarMusicaComando(
                request.cpf(), request.musicaId(), request.titulo(), request.artista()
        );
        return ResponseEntity.ok(UsuarioResponse.de(servicoFavoritarMusica.favoritar(comando)));
    }

    @Override
    @PostMapping("/playlists")
    public ResponseEntity<PlaylistResponse> criarPlaylist(@Valid @RequestBody CriarPlaylistRequest request) {
        var comando = new CriarPlaylistComando(request.cpf(), request.nome());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlaylistResponse.de(servicoPlaylist.criar(comando)));
    }

    @Override
    @PostMapping("/playlists/musicas")
    public ResponseEntity<UsuarioResponse> adicionarMusicaNaPlaylist(
            @Valid @RequestBody AdicionarMusicaPlaylistRequest request
    ) {
        var comando = new AdicionarMusicaPlaylistComando(
                request.cpf(),
                request.nomePlaylist(),
                request.musicaId(),
                request.titulo(),
                request.artista()
        );
        return ResponseEntity.ok(UsuarioResponse.de(servicoPlaylist.adicionarMusica(comando)));
    }
}
