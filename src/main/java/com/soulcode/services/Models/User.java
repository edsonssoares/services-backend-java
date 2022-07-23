package com.soulcode.services.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto incremento
    private Integer idUser;

    @Column(unique = true, nullable = false) // é único e não pode ser nulo 
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // para senha/hash não aparecer na resposta do Json
    private String password;



    // getters e setters

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
