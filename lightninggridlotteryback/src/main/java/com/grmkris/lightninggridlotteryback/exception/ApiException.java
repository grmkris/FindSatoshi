package com.grmkris.lightninggridlotteryback.exception;

public class ApiException extends Exception {
    
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param errorMessage
     */
    public ApiException(String errorMessage) {
        super(errorMessage);
    }
}