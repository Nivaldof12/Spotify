package com.streammuse.application.biblioteca;

public record AdicionarMusicaPlaylistComando(
        String cpf,
        String nomePlaylist,
        String musicaId,
        String titulo,
        String artista
) {
}
