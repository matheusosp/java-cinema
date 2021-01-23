package com.cinema.gerenciamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IdNaoEncontradoException  extends Exception{

    public IdNaoEncontradoException(String message){
        super(message);
    }

}
