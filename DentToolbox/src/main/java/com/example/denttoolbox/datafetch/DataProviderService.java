package com.example.denttoolbox.datafetch;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.example.denttoolbox.entity.ErrorSingleton;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DataProviderService {

    public String basicUrl = "";
    public String userpass = "";
    public String basicAuth = "";


    public String errorMessage = "";

    public CredentialsSingleton data = CredentialsSingleton.getInstance();
    public InventorySingleton inventoryData = InventorySingleton.getInstance();

    public HttpClient client;

    public ErrorSingleton errorSingleton = ErrorSingleton.getInstance();

    public int login_request(){

        int responseCode = 0;

        try{

            basicUrl = "http://" + data.getHostname() + ":" + data.getPortNumber() + "/api";
            userpass = data.getUsername() + ":" + data.getPassword();
            basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));

            URL url = new URL(basicUrl);
            System.out.println(url + " <<<<< URL");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", basicAuth);
            conn.setRequestMethod("GET");
            conn.connect();

            responseCode = conn.getResponseCode();
            data.setResponseCode(responseCode);

            conn.setConnectTimeout(10);
            conn.disconnect();

        } catch (IllegalArgumentException e) {
            errorMessage = "The port you passeed is out of range";
            return 100;
        } catch (RuntimeException e) {
            errorMessage = "Please provide all pieces of information";
        } catch (ProtocolException e) {
            System.out.println("I am in protocol exception");
            return 100;

        } catch (MalformedURLException e) {
            errorMessage = "MalformedURLException occured";
            return 102;

        } catch (IOException e) {
            errorMessage = "IOException has occured";
            return 103;

        }
        return data.getResponseCode();
    }

    public void getEntities(){
        inventoryData.setSiteUsersNumber(numberGetRequest("siteUser"));
        inventoryData.setClientsNumber(numberGetRequest("client"));
        inventoryData.setSitesNumber(numberGetRequest("site"));

        getDistributes();
    }

    public String getDistributes(){
        String numberOfEntities = "";

        client = HttpClient.newHttpClient();

        basicUrl = "http://" + data.getHostname() + ":" + data.getPortNumber() + "/api/operations/pending?allUsers=false";
        userpass = data.getUsername() + ":" + data.getPassword();
        basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));

        String response_body = "";

        try{
            HttpRequest req = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(basicUrl))
                    .header("Authorization",basicAuth)
                    .header("Content-Type","application/json")
                    .build();
            HttpResponse response = client.send(req, HttpResponse.BodyHandlers.ofString());
            String responseString = (String) response.body();
            response_body = responseString;

            String [] pendingChanges =  responseString.split("type");
            inventoryData.setNumberOfPendingDistributes(pendingChanges.length - 1 );



        }catch(URISyntaxException e){
            System.out.println("JESTEM W URI");
        }catch(IOException e){
            System.out.println("JESTEM W IOEX");
        }catch(InterruptedException e){
            System.out.println("JESTEM W INTER");
        }
        return response_body;

    }

    public String numberGetRequest(String endpoint) {

        String numberOfEntities = "";

        client = HttpClient.newHttpClient();

        basicUrl = "http://" + data.getHostname() + ":" + data.getPortNumber() + "/api/" +
                endpoint + "s?direction=desc";
        userpass = data.getUsername() + ":" + data.getPassword();
        basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));

        try{
            HttpRequest req = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(basicUrl))
                    .header("Authorization",basicAuth)
                    .header("Content-Type","application/json")
                    .build();
            HttpResponse response = client.send(req, HttpResponse.BodyHandlers.ofString());
            String responseString = (String) response.body();

            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(responseString);

            getLastItemName(json, endpoint);

            for (Map.Entry<String, JsonElement> key :json.entrySet()){
                if(key.getKey().equals("totalElements")){
                    return String.valueOf(key.getValue());
                }
            }

        }catch(URISyntaxException e){
            System.out.println("JESTEM W URI");
        }catch(IOException e){
            System.out.println("JESTEM W IOEX");
        }catch(InterruptedException e){
            System.out.println("JESTEM W INTER");
        }
        return "0";
    }

    public void getLastItemName(JsonObject json, String endpoint){

        String entityName = "";
        List<String> endpointNames = new ArrayList<String>();

        for(Map.Entry<String, JsonElement> key : json.entrySet()){
            if (key.getKey().equals("content")){
                JsonElement elements = key.getValue();
                for (JsonElement e : elements.getAsJsonArray() ){
                    if (endpoint.equals("siteUser")){
                        entityName = "loginName";
                    }else{
                        entityName = endpoint + "Name";
                    }
                    String endpointName = e.getAsJsonObject().get(entityName).getAsString();
                    endpointNames.add(endpointName);
                }
            }
        }

        switch(endpoint){
            case "client" -> inventoryData.setLasClientName(endpointNames.get(0));
            case "site" -> inventoryData.setLastSiteName(endpointNames.get(0));
            case "siteUser" -> inventoryData.setLastSiteUserName(endpointNames.get(0));
        }
    }
    public void sendRequest() throws IOException, InterruptedException {

        String basicUrl = "http://localhost:8090/api/clients";
        String userpass = "administrator:administrator";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));

        String clientName ="kl1";
        String secondaryName = "sec";
        String addr1 = "dwsdw";
        String addr2 = "qwdqwd";
        String addr3 = "jsjssj";
        String postCode = "wewedwed";
        String reportingId = "dwedwed";
        String country = "Counteryy";
        String stateProvince = "provicne";
        String city = "cittyty";

        String fullPayload = "{\"clientName\":\"" + clientName +
                "\",\"clientType\":\"VENDOR\"," +
                "\"clientName2\":\"" + secondaryName +
                "\",\"address1\":\"" + addr1 +
                "\",\"address2\":\"" + addr2 +
                "\",\"address3\":\"" + addr3 +
                "\",\"postCode\":\"" + postCode +
                "\",\"vendorReportingId\":\"" + reportingId +
                "\",\"country\":\"" + country +
                "\",\"stateProvince\":\"" + stateProvince +
                "\",\"city\":\"" + city +  "\"}";

        client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Authorization", basicAuth)
                .setHeader("Content-Type", "application/json")
                .uri(URI.create(basicUrl))
                .POST(HttpRequest.BodyPublishers.ofString(fullPayload))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response);
    }

    public String getSitesId(String siteName){

        String siteId = "";

        client = HttpClient.newHttpClient();
        userpass = data.getUsername() + ":" + data.getPassword();
        basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));

        basicUrl = "http://" + data.getHostname() + ":" + data.getPortNumber() + "/api/sites";

        try{
            HttpRequest req = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(basicUrl))
                    .header("Authorization",basicAuth)
                    .header("Content-Type","application/json")
                    .build();
            HttpResponse response = client.send(req, HttpResponse.BodyHandlers.ofString());
            String responseString = (String) response.body();

            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(responseString);

            siteId = getLastItemId(json, siteName, "site");

        }catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return siteId;
    }
    public String getLastItemId(JsonObject json, String entityName, String endpoint){
        String entityId = "";
        List<String> endpointNames = new ArrayList<String>();

        for(Map.Entry<String, JsonElement> key : json.entrySet()) {
            if (key.getKey().equals("content")){
                JsonElement elements = key.getValue();
                for (JsonElement e : elements.getAsJsonArray()){
                    if (entityName.equals(e.getAsJsonObject().get("siteName").getAsString())){
                        return String.valueOf(e.getAsJsonObject().get("id"));
                    }
                }
            }
        }
        return entityId;
    }

    public int getNumberOfUsersBelongingToASite(String siteId){

        int numberOfUsers = 0;

        client = HttpClient.newHttpClient();
        userpass = data.getUsername() + ":" + data.getPassword();
        basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));

        basicUrl = "http://" + data.getHostname() + ":" + data.getPortNumber() + "/api/sites/"  + siteId +
            "/siteUsers";

        try{
            HttpRequest req = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(basicUrl))
                    .header("Authorization",basicAuth)
                    .header("Content-Type","application/json")
                    .build();
            HttpResponse response = client.send(req, HttpResponse.BodyHandlers.ofString());
            String responseString = (String) response.body();

            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(responseString);

            for(Map.Entry<String, JsonElement> key : json.entrySet()) {
                if(key.getKey().equals("content")){
                    key.getValue().getAsJsonArray().size();
                    numberOfUsers = key.getValue().getAsJsonArray().size();
                }
            }

        }catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return numberOfUsers;
    }
    public String getRequestBody(String service){

        String requestBody = "";
        String beginning = "[";
        String end = "]";
        String singleService =  "{\"serviceName\":\"" + service.strip() + "\"}";
        requestBody = beginning + singleService+ end;
        return requestBody;
    }
    public int postServices(String url, String body, String basicAuth) throws IOException, InterruptedException {

        client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Authorization", basicAuth)
                .setHeader("Content-Type", "application/json")
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> res= client.send(request,
                HttpResponse.BodyHandlers.ofString());

        checkForFailedRequest(res, url, body);
        return 1;
    }

    public int deleteServices(String url, String body, String basicAuth) throws URISyntaxException, IOException, InterruptedException {

        client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .method("DELETE",HttpRequest.BodyPublishers.ofString(body))
                .header("Authorization",basicAuth)
                .header("Content-Type", "application/json")
                .build();
        var resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        checkForFailedRequest(resp, url, body);

        return 1;
    }

    public ArrayList<String> getUsersBelongingToASite(String siteId) throws URISyntaxException, IOException, InterruptedException {

        ArrayList<String> usersIds = new ArrayList<>();

        client = HttpClient.newHttpClient();
        userpass = data.getUsername() + ":" + data.getPassword();
        basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));

        basicUrl = "http://" + data.getHostname() + ":" + data.getPortNumber() + "/api/sites/"  + siteId +
                "/siteUsers?page=";

        String zeroUrl = basicUrl + "0";
        client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(zeroUrl))
                .GET()
                .header("Authorization",basicAuth)
                .header("Content-Type", "application/json")
                .build();
        var resp = client.send(request, HttpResponse.BodyHandlers.ofString());


        String responseString = resp.body().toString();
        String totalPages = responseString.split("totalPages")[1];
        String pageNumber = totalPages.split(",")[0];
        int numberOfPagesInt = Integer.parseInt(pageNumber.split(": ")[1]);

        for (int i = 0; i <= numberOfPagesInt -1 ; i++){

            HttpRequest userRequest = HttpRequest.newBuilder()
                    .uri(new URI(basicUrl + i))
                    .GET()
                    .header("Authorization",basicAuth)
                    .header("Content-Type", "application/json")
                    .build();
            var userResp = client.send(userRequest, HttpResponse.BodyHandlers.ofString());
            usersIds.addAll(getsiteUsersIds(userResp.body()));
        }

        return usersIds;
    }

    public void checkForFailedRequest(HttpResponse<String> resp, String url, String body){

        System.out.println(resp.statusCode());

        if (resp.statusCode() != 200){
            String[] newError = {resp.body(), url, body};
            errorSingleton.addError(newError);
        }
    }

    public ArrayList<String> getsiteUsersIds(String response){
        ArrayList<String> ids = new ArrayList<>();

        String[] user_objects = response.split("status");

        for(String siteUserObj : user_objects){
            if (siteUserObj.contains("loginName")){
                String item = siteUserObj.split("\"id\" : ")[1];
                String siteUserId = item.split(",")[0];
                ids.add(siteUserId);
            }
        }
        return ids;
    }

    public void postClient(String clientName,
                           String secondaryName,
                           String addr1,
                           String addr2,
                           String addr3,
                           String city,
                           String postCode,
                           String country,
                           String reportingId,
                           String stateProvince) throws IOException, InterruptedException {

        client = HttpClient.newHttpClient();
        userpass = data.getUsername() + ":" + data.getPassword();
        basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));

        basicUrl = "http://" + data.getHostname() + ":" + data.getPortNumber() + "/api/clients";

        String fullPayload = "{\"clientName\":\"" + clientName +
                "\",\"clientType\":\"VENDOR\"," +
                "\"clientName2\":\"" + secondaryName +
                "\",\"address1\":\"" + addr1 +
                "\",\"address2\":\"" + addr2 +
                "\",\"address3\":\"" + addr3 +
                "\",\"postCode\":\"" + postCode +
                "\",\"vendorReportingId\":\"" + reportingId +
                "\",\"country\":\"" + country +
                "\",\"stateProvince\":\"" + stateProvince +
                "\",\"city\":\"" + city + "\"}";

        System.out.println(fullPayload);

        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Authorization", basicAuth)
                .setHeader("Content-Type", "application/json")
                .uri(URI.create(basicUrl))
                .POST(HttpRequest.BodyPublishers.ofString(fullPayload))
                .build();

        HttpResponse<String> res= client.send(request,
                HttpResponse.BodyHandlers.ofString());

        checkForFailedRequest(res, basicUrl, fullPayload);
    }

}
