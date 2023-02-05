package com.example.denttoolbox.entity;

import java.util.ArrayList;
import java.util.List;

public class SingleRowSiteUsers {

    private String operation; // can be add/delete
    private String siteId;
    private String siteName;
    private String siteUserId;
    private List<String> services;

    public SingleRowSiteUsers(){

    }

    public  SingleRowSiteUsers(String operation, String siteName,String siteUserId,  ArrayList<String> services){
        this.operation = operation;
        this.siteName = siteName;
        this.siteUserId = siteUserId;
        this.services = services;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteId(){
        return siteId;
    }

    public String getSiteUserId(){
        return  siteUserId;
    }

    public void setSiteUserId(String siteUserId){
        this.siteUserId = siteUserId;
    }

    public void setOperation(String operation){
        this.operation = operation;
    }

    public String getOperation(){
        return operation;
    }

    public void setServices(List<String> services){
        this.services = services;
    }

    public List<String> getServices(){
        return services;
    }

    public void setSiteName(String siteName){
        this.siteName = siteName;
    }

    public String getSiteName(){
        return siteName;
    }

    @Override
    public String toString(){
        return getSiteId() + ", " +
                getSiteName() + ", " +
                getSiteUserId() + ", " +
                getOperation() + ", " +
                getServices();
    }

}


