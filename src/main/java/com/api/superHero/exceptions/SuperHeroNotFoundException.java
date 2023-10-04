package com.api.superHero.exceptions;

public class SuperHeroNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public SuperHeroNotFoundException(String msg) {
        super(msg);
    }
}
