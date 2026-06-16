package com.streammuse.domain.conta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "assinaturas_ativas")
public class AssinaturaAtiva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPlanoAssinatura plano;

    @Column(nullable = false)
    private Instant dataAtivacao;

    @Column(nullable = false)
    private boolean ativa;

    protected AssinaturaAtiva() {
    }

    private AssinaturaAtiva(Usuario usuario, TipoPlanoAssinatura plano) {
        this.usuario = usuario;
        this.plano = plano;
        this.dataAtivacao = Instant.now();
        this.ativa = true;
    }

    static AssinaturaAtiva criar(Usuario usuario, TipoPlanoAssinatura plano) {
        return new AssinaturaAtiva(usuario, plano);
    }

    public boolean estaAtiva() {
        return ativa;
    }

    public TipoPlanoAssinatura getPlano() {
        return plano;
    }

    public Instant getDataAtivacao() {
        return dataAtivacao;
    }
}
