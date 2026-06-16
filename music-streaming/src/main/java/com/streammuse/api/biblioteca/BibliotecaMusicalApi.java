package com.streammuse.api.biblioteca;

import com.streammuse.infrastructure.web.dto.AdicionarMusicaPlaylistRequest;
import com.streammuse.infrastructure.web.dto.CriarPlaylistRequest;
import com.streammuse.infrastructure.web.dto.FavoritarMusicaRequest;
import com.streammuse.infrastructure.web.dto.PlaylistResponse;
import com.streammuse.infrastructure.web.dto.UsuarioResponse;
import org.springframework.http.ResponseEntity;

public interface BibliotecaMusicalApi {

    ResponseEntity<UsuarioResponse> favoritarMusica(FavoritarMusicaRequest request);

    ResponseEntity<PlaylistResponse> criarPlaylist(CriarPlaylistRequest request);

    ResponseEntity<UsuarioResponse> adicionarMusicaNaPlaylist(AdicionarMusicaPlaylistRequest request);
}
