package com.streammuse.domain.conta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cartoes_credito")
public class CartaoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private boolean valido;

    @Column(nullable = false)
    private boolean ativo;

    protected CartaoCredito() {
    }

    private CartaoCredito(String numero, boolean valido, boolean ativo) {
        this.numero = numero;
        this.valido = valido;
        this.ativo = ativo;
    }

    public static CartaoCredito criar(String numero, boolean valido, boolean ativo) {
        return new CartaoCredito(numero, valido, ativo);
    }

    void vincularUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean eValido() {
        return valido;
    }

    public boolean estaAtivo() {
        return ativo;
    }

    public String getNumero() {
        return numero;
    }
}
