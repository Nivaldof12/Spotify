package com.streammuse.domain.conta;

import com.streammuse.domain.shared.Cpf;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "valor", column = @Column(name = "cpf", nullable = false, unique = true, length = 11))
    )
    private Cpf cpf;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private CartaoCredito cartaoCredito;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private AssinaturaAtiva assinaturaAtiva;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MusicaFavorita> musicasFavoritas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playlist> playlists = new ArrayList<>();

    protected Usuario() {
    }

    private Usuario(Cpf cpf, String nome, String email, CartaoCredito cartaoCredito) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.cartaoCredito = cartaoCredito;
        cartaoCredito.vincularUsuario(this);
    }

    public static Usuario criarConta(Cpf cpf, String nome, String email, CartaoCredito cartaoCredito) {
        Objects.requireNonNull(cpf, "CPF é obrigatório");
        Objects.requireNonNull(nome, "Nome é obrigatório");
        Objects.requireNonNull(email, "E-mail é obrigatório");
        Objects.requireNonNull(cartaoCredito, "Cartão de crédito é obrigatório");
        return new Usuario(cpf, nome, email, cartaoCredito);
    }

    public void favoritarMusica(String musicaId, String titulo, String artista) {
        if (musicasFavoritas.stream().anyMatch(m -> m.getMusicaId().equals(musicaId))) {
            return;
        }
        musicasFavoritas.add(MusicaFavorita.criar(this, musicaId, titulo, artista));
    }

    public Playlist criarPlaylist(String nome) {
        Playlist playlist = Playlist.criar(this, nome);
        playlists.add(playlist);
        return playlist;
    }

    public void adicionarMusicaNaPlaylist(String nomePlaylist, String musicaId, String titulo, String artista) {
        Playlist playlist = playlists.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nomePlaylist))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Playlist não encontrada: " + nomePlaylist));
        playlist.adicionarMusica(musicaId, titulo, artista);
    }

    public void ativarAssinatura(TipoPlanoAssinatura plano) {
        if (assinaturaAtiva != null && assinaturaAtiva.estaAtiva()) {
            throw new IllegalStateException("plano-ativo-existente");
        }
        if (!cartaoCredito.eValido()) {
            throw new IllegalStateException("cartao-invalido");
        }
        this.assinaturaAtiva = AssinaturaAtiva.criar(this, plano);
    }

    public boolean possuiPlanoAtivo() {
        return assinaturaAtiva != null && assinaturaAtiva.estaAtiva();
    }

    public boolean possuiCartaoValido() {
        return cartaoCredito != null && cartaoCredito.eValido();
    }

    public boolean possuiCartaoAtivo() {
        return cartaoCredito != null && cartaoCredito.estaAtivo();
    }

    public Long getId() {
        return id;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public Optional<AssinaturaAtiva> getAssinaturaAtiva() {
        return Optional.ofNullable(assinaturaAtiva);
    }

    public List<MusicaFavorita> getMusicasFavoritas() {
        return List.copyOf(musicasFavoritas);
    }

    public List<Playlist> getPlaylists() {
        return List.copyOf(playlists);
    }
}
