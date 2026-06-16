package com.streammuse.domain.conta;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MusicaPlaylist> musicas = new ArrayList<>();

    protected Playlist() {
    }

    private Playlist(Usuario usuario, String nome) {
        this.usuario = usuario;
        this.nome = nome;
    }

    static Playlist criar(Usuario usuario, String nome) {
        return new Playlist(usuario, nome);
    }

    void adicionarMusica(String musicaId, String titulo, String artista) {
        if (musicas.stream().anyMatch(m -> m.getMusicaId().equals(musicaId))) {
            return;
        }
        musicas.add(MusicaPlaylist.criar(this, musicaId, titulo, artista));
    }

    public String getNome() {
        return nome;
    }

    public List<MusicaPlaylist> getMusicas() {
        return List.copyOf(musicas);
    }
}
