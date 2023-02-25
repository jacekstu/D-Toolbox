package com.example.denttoolbox.datafetch;

import com.example.denttoolbox.entity.ClientSingletonClass;
import javafx.concurrent.Task;

public class CreateEntityTask extends Task<Integer> {

    private final int numberOfEntitiesToCreate;
    private final String entity;

    public CreateEntityTask(int numberOfEntitiesToCreate, String entity){
        this.numberOfEntitiesToCreate = numberOfEntitiesToCreate;
        this.entity = entity;
    }

    @Override
    protected Integer call() throws Exception {

        DataProviderService dps = new DataProviderService();
        ClientSingletonClass csc = ClientSingletonClass.getInstance();

        int already_created = 0;

        while(already_created != (numberOfEntitiesToCreate)){
            dps.postClient(
                    already_created,
                    csc.getClientName(),
                    csc.getSecondaryName(),
                    csc.getAddress1(),
                    csc.getAddress2(),
                    csc.getAddress3(),
                    csc.getCity(),
                    csc.getPostCode(),
                    csc.getCountry(),
                    csc.getVendorReportingId(),
                    csc.getStateProvince()
            );
            already_created += 1;
            updateValue(already_created);
        }
        return already_created;
    }

}
