package com.streammuse.infrastructure.web;

import com.streammuse.api.pagamento.AutorizacaoTransacaoApi;
import com.streammuse.application.pagamento.AutorizarTransacaoComando;
import com.streammuse.application.pagamento.ServicoAutorizacaoTransacao;
import com.streammuse.infrastructure.web.dto.AutorizarTransacaoRequest;
import com.streammuse.infrastructure.web.dto.TransacaoResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController implements AutorizacaoTransacaoApi {

    private final ServicoAutorizacaoTransacao servicoAutorizacaoTransacao;

    public TransacaoController(ServicoAutorizacaoTransacao servicoAutorizacaoTransacao) {
        this.servicoAutorizacaoTransacao = servicoAutorizacaoTransacao;
    }

    @Override
    @PostMapping("/autorizar")
    public ResponseEntity<TransacaoResponse> autorizarTransacao(@Valid @RequestBody AutorizarTransacaoRequest request) {
        var comando = new AutorizarTransacaoComando(request.cpf(), request.valor(), request.comerciante());
        return ResponseEntity.ok(TransacaoResponse.de(servicoAutorizacaoTransacao.autorizar(comando)));
    }
}
