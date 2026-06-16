package com.streammuse.domain.conta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "musicas_playlist")
public class MusicaPlaylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @Column(nullable = false)
    private String musicaId;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String artista;

    protected MusicaPlaylist() {
    }

    private MusicaPlaylist(Playlist playlist, String musicaId, String titulo, String artista) {
        this.playlist = playlist;
        this.musicaId = musicaId;
        this.titulo = titulo;
        this.artista = artista;
    }

    static MusicaPlaylist criar(Playlist playlist, String musicaId, String titulo, String artista) {
        return new MusicaPlaylist(playlist, musicaId, titulo, artista);
    }

    public String getMusicaId() {
        return musicaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }
}
