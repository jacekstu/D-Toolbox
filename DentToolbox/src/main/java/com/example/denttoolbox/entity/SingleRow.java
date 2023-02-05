package com.example.denttoolbox.entity;

import java.util.ArrayList;
import java.util.List;

public class SingleRow {

    private String operation; // can be add/delete
    private String siteId;
    private String siteName;
    private List<String> services;

    public SingleRow(){

    }

    public SingleRow(String operation, String siteName, ArrayList<String>services){
        this.operation = operation;
        this.siteName = siteName;
        this.services = services;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteId(){
        return siteId;
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
                getOperation() + ", " +
                getServices();
    }

}


