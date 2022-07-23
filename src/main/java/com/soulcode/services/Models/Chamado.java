package com.soulcode.services.Models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

// toda classe tem que ter entity porque representa um modelo pelo qual table será construida no mysql
@Entity

public class Chamado {

    @Id
    private Integer idChamado;

    @Column(nullable = false) // é obrigatório
    private String titulo;

    @Column(nullable = true) // não é obrigatório
    private String descricao;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(columnDefinition = "date", nullable = false)
    private Date dataEntrada;

    // @Column não é obrigatório, apenas se houver necessidade de inserir configurações
    @Enumerated(EnumType.STRING)
    private StatusChamado status;

    // Relacionamentos entre tables
    // Chaves Estrangeiras
    // ManyToOne - relacionamento de muitos para um

    // muitos chamados para um funcionário
    @ManyToOne
    @JoinColumn(name = "idFuncionario")
    private Funcionario funcionario;

    // muitos chamados para um cliente
    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    // um objeto tipo pagamento
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPagamento", unique = true)
    private Pagamento pagamento;


    public Integer getIdChamado() {
        return idChamado;
    }

    public void setIdChamado(Integer idChamado) {
        this.idChamado = idChamado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
