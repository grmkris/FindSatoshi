package com.grmkris.lightninggridlotteryback.exception;

public class RoundNotFoundException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RoundNotFoundException(String errorMessage) {
        super("round not found!");
    }
}