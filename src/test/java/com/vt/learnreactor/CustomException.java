package com.vt.learnreactor;

public class CustomException extends Throwable {
    private String message;
    public CustomException(Throwable ex) {
        this.message = ex.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
