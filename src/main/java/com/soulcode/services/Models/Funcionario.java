package com.soulcode.services.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// toda classe tem que ter entity porque representa um modelo pelo qual table será construida no mysql
@Entity
// criando a table Funcionario
public class Funcionario {
    // @ significa anotação

    @Id // key primary of table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment - sem regras
    private Integer idFuncionario;

    @Column(nullable = false, length = 100) // não pode ser nulo / é obrigatório. 100 carcteres
    private String nome;

    @Column(nullable = false, length = 100, unique = true) // email unique de funcionário
    private String email;

    @Column(nullable = true) // posso cadastrar o funcionario sem foto / não é obrigatório
    private String foto;

    // Relacionamento entre tables
    // Um funcionário Para Muitos chamados
    //JsonIgnore impede o loop de informações e não cria coluna na tabela
    @JsonIgnore
    @OneToMany(mappedBy = "funcionario")
    private List<Chamado> chamados = new ArrayList<Chamado>();

    // Muitos funcionários Para Um cargo
    //pela regra de negócio e lógica a chave estrangeira foi inserida na table funcionário
    // quando eu chamar o funcionário mostrará o cargo
    @ManyToOne
    @JoinColumn(name = "idCargo")
    private Cargo cargo;


    // getters e setters
    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {

        this.idFuncionario = idFuncionario;
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}


