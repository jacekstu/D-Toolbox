package com.example.denttoolbox.exceptions;

public class SingleOptionException extends Exception{

    private String code;
    private String message;

    public SingleOptionException (String code, String message){
        super(message);
        this.setCode(code);
        this.setMessage(message);
    }

    public SingleOptionException (String code, String message, Throwable cause){
        super(message, cause);
        this.setCode(code);
        this.setCode(message);
    }

    public void setCode(String code){
        this.code = code;
    }
    public void setMessage(String message){this.message = message;}

    public String getcode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
