package com.soulcode.services.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// toda classe tem que ter entity porque representa um modelo pelo qual table será construida no mysql
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100, unique = true) // unique - só pode ter 1 email
    private String email;

    // Relacionamento entre tables
    // Um cliente para muitos chamados
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Chamado> chamados = new ArrayList<Chamado>();

    // Um cliente Para Um Endereço
    // cascade = efeito cascata - todas as alterações feitas aqui irá acontecer em table enderecos
    // o cascate também terá que ser colocado no mysql na table cliente. chave de boca - foreign keys - foreign keys options - nos 2
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idEndereco", unique = true)
    private Endereco endereco;




    // getters e setters
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {

        this.chamados = chamados;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
