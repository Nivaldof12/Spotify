package com.streammuse.infrastructure.web.dto;

import com.streammuse.domain.conta.Playlist;

import java.util.List;

public record PlaylistResponse(
        String nome,
        List<MusicaResponse> musicas
) {

    public static PlaylistResponse de(Playlist playlist) {
        return new PlaylistResponse(
                playlist.getNome(),
                playlist.getMusicas().stream()
                        .map(m -> new MusicaResponse(m.getMusicaId(), m.getTitulo(), m.getArtista()))
                        .toList()
        );
    }
}
