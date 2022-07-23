package com.soulcode.services.Models;

public enum StatusChamado {

    // no mysql na column status todas as letras em MAIUSCULAS
    RECEBIDO("Recebido"),
    ATRIBUIDO("Atribuido"),
    CONCLUIDO("Concluido"),
    ARQUIVADO("Arquivado");

    private String conteudo;

    StatusChamado(String conteudo) {

        this.conteudo = conteudo;
    }

    public String getConteudo() {

        return conteudo;
    }


}
