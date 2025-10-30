package com.agi.hack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // Retorna 400 quando lançada
public class ManutencaoException extends RuntimeException {
    public ManutencaoException(String message) {
        super(message);
    }
}
