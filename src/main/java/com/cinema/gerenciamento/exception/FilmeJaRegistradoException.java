package com.cinema.gerenciamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FilmeJaRegistradoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FilmeJaRegistradoException(String message){
        super(message);
    }

}
