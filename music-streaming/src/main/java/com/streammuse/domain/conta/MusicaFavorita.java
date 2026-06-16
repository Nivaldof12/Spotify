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
@Table(name = "musicas_favoritas")
public class MusicaFavorita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String musicaId;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String artista;

    protected MusicaFavorita() {
    }

    private MusicaFavorita(Usuario usuario, String musicaId, String titulo, String artista) {
        this.usuario = usuario;
        this.musicaId = musicaId;
        this.titulo = titulo;
        this.artista = artista;
    }

    static MusicaFavorita criar(Usuario usuario, String musicaId, String titulo, String artista) {
        return new MusicaFavorita(usuario, musicaId, titulo, artista);
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
