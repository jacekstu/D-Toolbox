package com.example.denttoolbox.datafetch;

import com.example.denttoolbox.entity.RowDataSingleton;
import com.example.denttoolbox.entity.RowDataUsersSingleton;
import com.example.denttoolbox.entity.SingleRow;
import com.example.denttoolbox.entity.SingleRowSiteUsers;
import javafx.concurrent.Task;

import java.util.Base64;
import java.util.List;

public class PostServiceTask extends Task<Integer> {

    public int alreadyCreated = 0;
    public RowDataSingleton rds = RowDataSingleton.getInstance();
    public RowDataUsersSingleton rdus = RowDataUsersSingleton.getInstance();
    public DataProviderService dps = new DataProviderService();

    public CredentialsSingleton cs = CredentialsSingleton.getInstance();

    private String endpoint;

    public PostServiceTask(String endpoint){
        this.endpoint = endpoint;
    }

    @Override
    protected Integer call() throws Exception {

        String service = "";
        String basicURL = "http://localhost:8090/api/";
        String userpass = cs.getUsername() + ":" + cs.getPassword();
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));

        if (endpoint.equals("Sites")){

            for (SingleRow s : rds.getRows()) {
                for(String serv : s.getServices()){
                    service = dps.getRequestBody(serv);
                    if (s.getOperation().equals("add")){
                        int req = dps.postServices(basicURL + "sites/" + dps.getSitesId(s.getSiteName()) + "/services", service, basicAuth);
                    }
                    else if (s.getOperation().equals("delete")){
                        int req = dps.deleteServices(basicURL + "sites/" + dps.getSitesId(s.getSiteName()) + "/services", service, basicAuth);
                    }
                    alreadyCreated += 1;
                    updateValue(alreadyCreated);
                }
            }
            return alreadyCreated;
        }else if (endpoint.equals("Users")){
            // The outer loop iterates over the add/delete services rows
            for(SingleRowSiteUsers s :rdus.getRows()){
                // first inner loop, get the service and generate the payload body
                for(String serv : s.getServices()){
                    // generate the payload body
                    service = dps.getRequestBody(serv);
                    if (s.getOperation().equals("add")){
                        int req = dps.postServices(basicURL + "siteUsers/" + s.getSiteUserId() + "/services", service, basicAuth);
                    }else if(s.getOperation().equals("delete")){
                        int req = dps.deleteServices(basicURL + "siteUsers/" + s.getSiteUserId() + "/services", service, basicAuth);
                    }
                    alreadyCreated += 1;
                    updateValue(alreadyCreated);
                }
            }
            return alreadyCreated;
        }else{
            return 1;
        }
    }

    public String getEndpoint(){
        return endpoint;
    }

    public void setEndpoint(String endpoint){
        this.endpoint = endpoint;
    }
}