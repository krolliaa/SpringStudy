package com.zwm.exception;

public class GoodsNullPointerException extends Exception {

    private String message;

    public GoodsNullPointerException() {
        super();
    }

    public GoodsNullPointerException(String message) {
        super(message);
        this.message = message;
    }
}
