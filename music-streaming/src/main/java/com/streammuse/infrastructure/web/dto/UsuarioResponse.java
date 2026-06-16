package com.streammuse.infrastructure.web.dto;

import com.streammuse.domain.conta.Usuario;

import java.util.List;

public record UsuarioResponse(
        String cpf,
        String nome,
        String email,
        boolean cartaoValido,
        boolean cartaoAtivo,
        String planoAtivo,
        List<MusicaResponse> favoritas,
        List<PlaylistResponse> playlists
) {

    public static UsuarioResponse de(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getCpf().getValor(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.possuiCartaoValido(),
                usuario.possuiCartaoAtivo(),
                usuario.getAssinaturaAtiva()
                        .filter(a -> a.estaAtiva())
                        .map(a -> a.getPlano().name())
                        .orElse(null),
                usuario.getMusicasFavoritas().stream()
                        .map(m -> new MusicaResponse(m.getMusicaId(), m.getTitulo(), m.getArtista()))
                        .toList(),
                usuario.getPlaylists().stream()
                        .map(PlaylistResponse::de)
                        .toList()
        );
    }
}
