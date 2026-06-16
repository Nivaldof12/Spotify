package com.streammuse.domain.pagamento;

import com.streammuse.domain.shared.Cpf;
import com.streammuse.domain.shared.StatusTransacao;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "valor", column = @Column(name = "cpf_usuario", nullable = false, length = 11))
    )
    private Cpf cpfUsuario;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private String comerciante;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTransacao status;

    @Column(nullable = false)
    private Instant dataHora;

    @Column
    private String motivoRecusa;

    protected Transacao() {
    }

    private Transacao(Cpf cpfUsuario, BigDecimal valor, String comerciante, StatusTransacao status, String motivoRecusa) {
        this.cpfUsuario = cpfUsuario;
        this.valor = valor;
        this.comerciante = comerciante;
        this.status = status;
        this.dataHora = Instant.now();
        this.motivoRecusa = motivoRecusa;
    }

    public static Transacao autorizar(Cpf cpfUsuario, BigDecimal valor, String comerciante) {
        return new Transacao(cpfUsuario, valor, comerciante, StatusTransacao.AUTORIZADA, null);
    }

    public static Transacao recusar(Cpf cpfUsuario, BigDecimal valor, String comerciante, String motivoRecusa) {
        return new Transacao(cpfUsuario, valor, comerciante, StatusTransacao.RECUSADA, motivoRecusa);
    }

    public boolean foiAutorizada() {
        return status == StatusTransacao.AUTORIZADA;
    }

    public boolean eSemelhanteA(BigDecimal outroValor, String outroComerciante) {
        return valor.compareTo(outroValor) == 0 && comerciante.equalsIgnoreCase(outroComerciante);
    }

    public Long getId() {
        return id;
    }

    public Cpf getCpfUsuario() {
        return cpfUsuario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getComerciante() {
        return comerciante;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public Instant getDataHora() {
        return dataHora;
    }

    public String getMotivoRecusa() {
        return motivoRecusa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transacao transacao)) {
            return false;
        }
        return Objects.equals(id, transacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
