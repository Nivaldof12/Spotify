package com.streammuse.infrastructure.web;

import com.streammuse.api.conta.GerenciamentoContaApi;
import com.streammuse.application.conta.CriarContaComando;
import com.streammuse.application.conta.ServicoCriacaoConta;
import com.streammuse.infrastructure.web.dto.CriarContaRequest;
import com.streammuse.infrastructure.web.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contas")
public class ContaController implements GerenciamentoContaApi {

    private final ServicoCriacaoConta servicoCriacaoConta;

    public ContaController(ServicoCriacaoConta servicoCriacaoConta) {
        this.servicoCriacaoConta = servicoCriacaoConta;
    }

    @Override
    @PostMapping
    public ResponseEntity<UsuarioResponse> criarConta(@Valid @RequestBody CriarContaRequest request) {
        var comando = new CriarContaComando(
                request.cpf(),
                request.nome(),
                request.email(),
                request.numeroCartao(),
                request.cartaoValido(),
                request.cartaoAtivo()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UsuarioResponse.de(servicoCriacaoConta.criarConta(comando)));
    }
}
