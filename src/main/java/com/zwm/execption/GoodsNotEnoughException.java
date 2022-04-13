package com.zwm.execption;

public class GoodsNotEnoughException extends Throwable {

    public GoodsNotEnoughException() {
    }

    public GoodsNotEnoughException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
