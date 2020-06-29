package com.grmkris.lightninggridlotteryback.exception;

public class ClaimException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public ClaimException(String error){
        super("Claim exception: " + error);
    }

}