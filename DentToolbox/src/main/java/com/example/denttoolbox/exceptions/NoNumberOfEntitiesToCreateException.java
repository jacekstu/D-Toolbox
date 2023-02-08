package com.example.denttoolbox.exceptions;

public class NoNumberOfEntitiesToCreateException extends Exception{

    private String code;

    public NoNumberOfEntitiesToCreateException(String code, String message){
        super(message);
        this.setCode(code);
    }

    public NoNumberOfEntitiesToCreateException(String code, String message, Throwable cause){
        super(message, cause);
        this.setCode(code);
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getcode(){
        return code;
    }
}
