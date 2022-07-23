package com.soulcode.services.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//1º passo criar a classe modelo
// 2º passo criar o repository
// 3º passo criar o service
// 4º passo criar o controler
@Entity
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCargo;

    @Column(nullable = true, length = 100)
    private String funcao;

    @Column(nullable = true)
    private String descricao;

    @Column(nullable = true)
    private double salario;

    // Relacionamento entre tables
    // Um cargo para muitos funcionários

    //JsonIgnore impede o loop de informações e não cria coluna na tabela
    @JsonIgnore
    @OneToMany(mappedBy = "cargo")
    private List<Funcionario> funcionarios = new ArrayList<Funcionario>();


    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }
}
