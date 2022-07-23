package com.soulcode.services.Controllers.Exceptions;

import java.time.Instant;

// StandardError - é usado para todos
public class StandardError {

    // esses objetos vieram do erro 500 do postman
    private Instant timestamp;
    private Integer status;
    private String error;
    private String trace;
    private String message;
    private String path;

    // constructor vazio só para instanciar os objetos dessa classe
    public StandardError() {}


    // getters e setters
    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
