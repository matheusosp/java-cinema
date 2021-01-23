package com.cinema.gerenciamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImpossivelDeletarException  extends Exception{

    public ImpossivelDeletarException(String message){
        super(message);
    }

}
