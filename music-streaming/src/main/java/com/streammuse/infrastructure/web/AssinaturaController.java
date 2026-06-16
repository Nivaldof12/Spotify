package com.streammuse.infrastructure.web;

import com.streammuse.api.assinatura.GerenciamentoAssinaturaApi;
import com.streammuse.application.assinatura.AssinarPlanoComando;
import com.streammuse.application.assinatura.ServicoAssinatura;
import com.streammuse.infrastructure.web.dto.AssinarPlanoRequest;
import com.streammuse.infrastructure.web.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assinaturas")
public class AssinaturaController implements GerenciamentoAssinaturaApi {

    private final ServicoAssinatura servicoAssinatura;

    public AssinaturaController(ServicoAssinatura servicoAssinatura) {
        this.servicoAssinatura = servicoAssinatura;
    }

    @Override
    @PostMapping
    public ResponseEntity<UsuarioResponse> assinarPlano(@Valid @RequestBody AssinarPlanoRequest request) {
        var comando = new AssinarPlanoComando(request.cpf(), request.plano());
        return ResponseEntity.ok(UsuarioResponse.de(servicoAssinatura.assinar(comando)));
    }
}
