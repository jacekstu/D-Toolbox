package com.example.denttoolbox.datafetch;

import javafx.concurrent.Task;

public class CreateEntityTask extends Task<Integer> {

    private final int numberOfEntitiesToCreate;

    public CreateEntityTask(int numberOfEntitiesToCreate){
        this.numberOfEntitiesToCreate = numberOfEntitiesToCreate;
    }

    @Override
    protected Integer call() throws Exception {
        return null;
    }
}
