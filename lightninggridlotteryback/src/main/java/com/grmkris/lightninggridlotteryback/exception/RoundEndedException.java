package com.grmkris.lightninggridlotteryback.exception;

public class RoundEndedException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RoundEndedException(String error) {
        super("round ended!");
    }
}