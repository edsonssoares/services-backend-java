package com.soulcode.services.Services.Exceptions;

public class EntityNotFoundException extends RuntimeException {

    // posso usar essa exceção em todas as classes Service da aplicação
    // esse erro é para mostrar que os dados não estão cadastrados no banco de dados
    //constructor
    public EntityNotFoundException(String msg) {

        super(msg);
    }
}
