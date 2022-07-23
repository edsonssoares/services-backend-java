package com.soulcode.services.Services.Exceptions;

public class DataIntegrityViolationException extends RuntimeException {

    // esse erro é para mostrar que os dados estão se repetindo
    public DataIntegrityViolationException(String msg) {
        super(msg);
    }
}
