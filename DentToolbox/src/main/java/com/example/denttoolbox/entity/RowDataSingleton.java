package com.example.denttoolbox.entity;

import java.util.ArrayList;

public class RowDataSingleton {

    private ArrayList<SingleRow> rows  = new ArrayList<>();

    public static final  RowDataSingleton instance = new RowDataSingleton();

    public RowDataSingleton(){

    }

    public static RowDataSingleton getInstance(){
        return instance;
    }

    public void setRows(ArrayList<SingleRow> rows){
        this.rows = rows;
    }

    public ArrayList<SingleRow> getRows(){
        return rows;
    }

    public int [] getStats(){

        int numberOfAddOperations = 0;
        int numberOfDeleteOperartions = 0;

        for(SingleRow row : rows){
            if (row.getOperation().equals("add")){
                // The number of add operations = the number of serivces to be assgined to the site
                numberOfAddOperations += row.getServices().size();
            }
            else if (row.getOperation().equals("delete")){
                numberOfDeleteOperartions += row.getServices().size();
            }
        }

        int [] stats = {numberOfAddOperations, numberOfDeleteOperartions};

        return stats;
    }

    public int getTotalNumberOfOperations(){

        int totalNumber = 0;

        for (SingleRow row : rows){
            totalNumber += row.getServices().size();
        }
        return totalNumber;
    }

}
