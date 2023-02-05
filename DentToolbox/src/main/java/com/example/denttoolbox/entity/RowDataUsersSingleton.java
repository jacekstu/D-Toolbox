package com.example.denttoolbox.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RowDataUsersSingleton {


    private ArrayList<SingleRowSiteUsers> rows  = new ArrayList<>();

    public static final  RowDataUsersSingleton instance = new RowDataUsersSingleton();

    public RowDataUsersSingleton(){

    }

    public static RowDataUsersSingleton getInstance(){
        return instance;
    }

    public void setRows(ArrayList<SingleRowSiteUsers> rows){
        this.rows = rows;
    }

    public ArrayList<SingleRowSiteUsers> getRows(){
        return rows;
    }



    public int [] getStats(){

        List<String> users_ids = new ArrayList<>();

        int numberOfAddOperations = 0;
        int numberOfDeleteOperartions = 0;

        for(SingleRowSiteUsers row : rows){
            if (row.getOperation().equals("add")){
                // The number of add operations = the number of serivces to be assgined to the site
                numberOfAddOperations += row.getServices().size();

            }
            else if (row.getOperation().equals("delete")){
                numberOfDeleteOperartions += row.getServices().size();
            }
            System.out.println(row.getSiteUserId());
            users_ids .add(row.getSiteUserId());
        }

        int [] stats = {numberOfAddOperations, numberOfDeleteOperartions};

        System.out.println(users_ids.toString() + "<----- tyle userow");
        System.out.println(users_ids.size() + " A TAKI JEST SAJZ");

        return stats;
    }

    public int getTotalNumberOfOperations(){

        int totalNumber = 0;

        for (SingleRowSiteUsers row : rows){
            totalNumber += row.getServices().size();
        }
        return totalNumber;
    }

}
