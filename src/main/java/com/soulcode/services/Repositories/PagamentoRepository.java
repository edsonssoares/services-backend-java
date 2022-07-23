package com.soulcode.services.Repositories;

import com.soulcode.services.Models.Chamado;
import com.soulcode.services.Models.Cliente;
import com.soulcode.services.Models.Funcionario;
import com.soulcode.services.Models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    // :status - é uma variável que significa que será substituido na requisição findByStatus(String status)
    // pagamento - é a table
    // status - é a coluna
    @Query(value = "SELECT * FROM pagamento WHERE status = :status",nativeQuery = true)
    List<Pagamento> findByStatus(String status);

    // List<List> - serão várias lista (table) em uma lista (table)
    // reunir vários dados de muitas tables em uma lista
    @Query(value = "SELECT pagamento.*, chamado.id_chamado, chamado.titulo, cliente.id_cliente, cliente.nome \n" +
            "\tFROM chamado RIGHT JOIN pagamento ON chamado.id_chamado = pagamento.id_pagamento \n" +
            "    LEFT JOIN cliente ON cliente.id_cliente = chamado.id_cliente",nativeQuery = true)
    List<List> orcamentoComServicoCliente();


}
