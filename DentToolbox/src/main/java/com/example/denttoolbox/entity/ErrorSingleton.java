package com.example.denttoolbox.entity;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ErrorSingleton {

    public ArrayList<String[]> errors = new ArrayList<>();

    public static final  ErrorSingleton instance = new ErrorSingleton();

    public ErrorSingleton(){

    }

    public void addError(String[] error){
        errors.add(error);
    }

    public static ErrorSingleton getInstance(){
        return instance;
    }

    public void flushErrors(){
        errors.clear();
    }

    public String listErrors(){

        String errorString = "";
        String separator = "\n-----------------------------------";
        int counter = 1;

        for(String [] err : errors){
            String errorNumber = "\nERROR " + String.valueOf(counter) + " OUT OF "
                    + String.valueOf(errors.size());

            errorString += separator + errorNumber + separator + "\nURL:" + err[1] + "\nPAYLOAD BODY:" + err[2]
                    + "\nRESPONSE:" + err[0];

            counter+=1;
        }
        errorString =  errorString + separator;

        return errorString;
    }

    public int getSize(){
        int error_size = errors.size();
        return error_size;
    }

}
