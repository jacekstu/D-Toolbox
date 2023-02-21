package com.example.denttoolbox.entity;

public class ClientSingletonClass {

    private static final ClientSingletonClass instance  = new ClientSingletonClass();

    private String clientName;
    private String secondaryName;
    private String clientType;
    private String address1;
    private String address2;
    private String address3;
    private String postCode;
    private String vendorReportingId;
    private String country;
    private String stateProvince;
    private String city;

    public static int counter;

    public ClientSingletonClass(){

    }

    public static ClientSingletonClass getInstance(){
        return instance;
    }

    public String getClientName() {
        return clientName;
    }

    public String getSecondaryName() {
        return secondaryName;
    }

    public String getClientType() {
        return clientType;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getVendorReportingId() {
        return vendorReportingId;
    }

    public String getCountry() {
        return country;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public String getCity() {
        return city;
    }

    public static int getCounter() {
        return counter;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setSecondaryName(String secondaryName) {
        this.secondaryName = secondaryName;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setVendorReportingId(String vendorReportingId) {
        this.vendorReportingId = vendorReportingId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static void setCounter(int counter) {
        ClientSingletonClass.counter = counter;
    }
}
