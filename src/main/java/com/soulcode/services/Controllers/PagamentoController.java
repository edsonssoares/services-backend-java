package com.soulcode.services.Controllers;


import com.soulcode.services.Models.Pagamento;
import com.soulcode.services.Repositories.PagamentoRepository;
import com.soulcode.services.Services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("services")
public class PagamentoController {

    @Autowired
    PagamentoService pagamentoService;

    @GetMapping("/pagamentos")
    public List<Pagamento> mostrarTodosPagamentos() {
        List<Pagamento> pagamentos = pagamentoService.mostrarTodosPagamentos();
        return pagamentos;
    }

    @GetMapping("/pagamentos/{idPagamento}")
    public ResponseEntity<Pagamento> mostrarUmPagamentoPeloId(@PathVariable Integer idPagamento) {
        Pagamento pagamento = pagamentoService.mostrarUmPagamentoPeloId(idPagamento);
        return ResponseEntity.ok().body(pagamento);
    }

    @GetMapping("/pagamentosPeloStatus")
    public List<Pagamento> mostrarPagamentosPeloStatus(@RequestParam("status") String status) {
        List<Pagamento> pagamentos = pagamentoService.mostrarPagamentosPeloStatus(status);
        return pagamentos;
    }

    @GetMapping("/pagamentosChamadosComCliente")
    public List<List> orcamentoComServicoCliente() {
        List<List> pagamentos = pagamentoService.orcamentoComServicoCliente();
        return pagamentos;
    }

    @PostMapping("/pagamentos/{idChamado}")
    public ResponseEntity<Pagamento> cadastrarPagamento(@PathVariable Integer idChamado, @RequestBody Pagamento pagamento) {
        pagamento = pagamentoService.cadastrarPagamento(pagamento, idChamado);

        URI novaURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pagamento.getIdPagamento()).toUri();
        return ResponseEntity.created(novaURI).body(pagamento);
    }

    @PutMapping("/pagamentos/{idPagamento}")
    public  ResponseEntity<Pagamento> editarPagamento(@PathVariable Integer idPagamento, @RequestBody Pagamento pagamento) {
        pagamento.setIdPagamento(idPagamento);
        pagamentoService.editarPagamento(pagamento);
        return ResponseEntity.ok().body(pagamento);
    }

    @PutMapping("/pagamentosModificarStatus/{idPagamento}")
    public ResponseEntity<Pagamento> modificarStatusPagamento(@PathVariable Integer idPagamento, @RequestParam("status") String status) {
        pagamentoService.modificarStatusPagamento(idPagamento, status);
        return ResponseEntity.noContent().build();
    }



}
