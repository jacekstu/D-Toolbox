package com.example.denttoolbox.datafetch;

public class PositionSingleton {

    private double x = 0;
    private double y = 0;

    private static final PositionSingleton instance = new PositionSingleton();

    private PositionSingleton(){
    }

    public static PositionSingleton getInstance(){
        return instance;
    }

    public void setX(double x){
        this.x = x;
    }

    public double getX(){
        return x;
    }
    public void setY(double y){
        this.y = y;
    }

    public double getY(){
        return y;
    }


}
