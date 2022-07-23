package com.soulcode.services.Services;


import com.soulcode.services.Models.Chamado;
import com.soulcode.services.Models.Cliente;
import com.soulcode.services.Models.Pagamento;
import com.soulcode.services.Models.StatusPagamento;
import com.soulcode.services.Repositories.ChamadoRepository;
import com.soulcode.services.Repositories.ClienteRepository;
import com.soulcode.services.Repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ChamadoRepository chamadoRepository;


    @Cacheable("pagamentosCache")
    public List<Pagamento> mostrarTodosPagamentos() {
        return pagamentoRepository.findAll();
    }

    @Cacheable(value = "pagamentosCache", key = "#idPagamento")
    public Pagamento mostrarUmPagamentoPeloId(Integer idPagamento) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagamento);
        return pagamento.orElseThrow();
    }

    public List<Pagamento> mostrarPagamentosPeloStatus(String status) {
        return pagamentoRepository.findByStatus(status);
    }

    @CachePut(value = "pagamentosCache", key = "#pagamento.idPagamento")
    public Pagamento cadastrarPagamento(Pagamento pagamento, Integer idChamado) {
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        if (chamado.isPresent()) {
            pagamento.setIdPagamento(idChamado);
            pagamento.setStatus(StatusPagamento.LANCADO);
            pagamentoRepository.save(pagamento);

            chamado.get().setPagamento(pagamento);
            chamadoRepository.save(chamado.get());
            return pagamento;
        } else {
            throw new RuntimeException();
        }

    }

    @CachePut(value = "pagamentosCache", key = "#pagamento.idPagamento")
    public Pagamento editarPagamento(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento modificarStatusPagamento(Integer idPagamento, String status) {
        Pagamento pagamento = mostrarUmPagamentoPeloId(idPagamento);

        switch (status) {
            case "LANCADO":
                pagamento.setStatus(StatusPagamento.LANCADO);
                break;

            case "QUITADO":
                pagamento.setStatus(StatusPagamento.QUITADO);
                break;
        }

        return pagamentoRepository.save(pagamento);

    }

    public List<List> orcamentoComServicoCliente() {
        return pagamentoRepository.orcamentoComServicoCliente();
    }


//    public Pagamento quitarPagamento(Integer idPagamento) {
//        Pagamento pagamento = mostrarUmPagamentoPeloId(idPagamento);
//        pagamento.setStatus(StatusPagamento.QUITADO);
//        return pagamentoRepository.save(pagamento);
//    }


}
