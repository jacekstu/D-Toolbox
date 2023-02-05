package com.example.denttoolbox.datafetch;

public class InventorySingleton {

    private String clientsNumber;

    private String sitesNumber;

    private String siteUsersNumber;

    private int numberOfPendingDistributes;

    private String lastClientName;
    private String lastSiteName;
    private String lastSiteUserName;


    private static final InventorySingleton instance = new InventorySingleton();

    private InventorySingleton(){

    }

    public static InventorySingleton getInstance(){
        return instance;
    }

    public String getClientsNumber(){
        return clientsNumber;
    }

    public void setClientsNumber(String clientsNumber){
        this.clientsNumber = clientsNumber;
    }

    public String getSitesNumber(){
        return sitesNumber;
    }

    public void setSitesNumber(String sitesNumber){
        this.sitesNumber = sitesNumber;
    }

    public String getSiteUsersNumber(){
        return siteUsersNumber;
    }

    public void setSiteUsersNumber(String siteUsersNumber){
        this.siteUsersNumber = siteUsersNumber;
    }

    public int getNumberOfPendingDistributes(){
        return numberOfPendingDistributes;
    }

    public void setNumberOfPendingDistributes(int numberOfPendingDistributes){
        this.numberOfPendingDistributes = numberOfPendingDistributes;
    }

    public void setLasClientName(String lastClientName){
        this.lastClientName = lastClientName;
    }

    public String getLastClientName(){
        return lastClientName;
    }

    public void setLastSiteName(String lastSiteName){
        this.lastSiteName = lastSiteName;
    }

    public String getLastSiteName(){
        return lastSiteName;
    }

    public void setLastSiteUserName(String lastSiteUserName){
        this.lastSiteUserName = lastSiteUserName;
    }

    public String getLastSiteUserName(){
        return lastSiteUserName;
    }


}
