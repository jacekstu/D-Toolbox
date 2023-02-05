package com.example.denttoolbox.entity;

public  class DistributeRow {

    private String type;

    private String name;

    private String action;

    private String user;

    private String hostname;

    public DistributeRow(){

    }
    public DistributeRow(String type, String name, String action, String user, String hostname) {
        this.type = type;
        this.name = addNewLine(name);
        //this.name = name;
        this.action = action;
        this.user = user;
        this.hostname = hostname;
    }

    public String addNewLine(String cellValue){

        String final_word = "";

        if (cellValue.contains(":")){
            String [] words = cellValue.split(":");
            for (String word : words){
                final_word += word.strip() + ":\n";
            }
            cellValue = final_word;
        }
        return cellValue;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

    public String getUser() {
        return user;
    }

    public String getHostname() {
        return hostname;
    }

    @Override
    public String toString(){
        return type + "TO JEST TYPWA";
    }
}