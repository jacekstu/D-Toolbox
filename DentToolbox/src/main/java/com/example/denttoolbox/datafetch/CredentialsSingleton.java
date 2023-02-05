package com.example.denttoolbox.datafetch;

public class CredentialsSingleton {

    private static final CredentialsSingleton instance = new CredentialsSingleton();

    private String username;
    private String password;
    private String hostname;
    private String portNumber;
    private int responseCode;

    private CredentialsSingleton(){
    }

    public static CredentialsSingleton getInstance(){
        return instance;
    }

    public String getUsername(){return username;}
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){return password;}
    public void setPassword(String password){
        this.password = password;
    }

    public String getHostname(){return hostname;}
    public void setHostname(String hostname){
        this.hostname = hostname;
    }

    public String getPortNumber(){return portNumber;}
    public void setPortNumber(String portNumber){
        this.portNumber = portNumber;
    }

    public int getResponseCode(){return responseCode;}
    public void setResponseCode(int responseCode){
        this.responseCode = responseCode;
    }

}
