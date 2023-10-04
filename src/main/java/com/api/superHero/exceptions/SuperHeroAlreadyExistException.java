package com.api.superHero.exceptions;

public class SuperHeroAlreadyExistException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public SuperHeroAlreadyExistException(String msg) {
        super(msg);
    }
}
