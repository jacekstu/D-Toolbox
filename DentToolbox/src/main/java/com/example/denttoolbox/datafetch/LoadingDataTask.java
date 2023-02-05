package com.example.denttoolbox.datafetch;

import com.example.denttoolbox.entity.RowDataSingleton;
import com.example.denttoolbox.entity.SingleRow;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class LoadingDataTask extends Task<Integer> {

    public List<String> items = new ArrayList<>();
    public DataProviderService dataProv = new DataProviderService();

    public ArrayList<SingleRow> rows = new ArrayList<>();

    public LoadingDataTask(List<String> items ){
        this.items = items;
    }

    public RowDataSingleton rds = RowDataSingleton.getInstance();

    @Override
    protected Integer call() throws Exception {
        String operation, site, id_of_a_site, servicesString;
        List<String> services = new ArrayList<>();

        int length_of_items = items.size();

        for (String item: items){

            if (item.length() > 0){

                String [] item_size = item.split(":");
                if (item_size.length == 4) {

                    SingleRow row = new SingleRow();
                    operation = item.split(":")[0];
                    site = item.split(":")[2];
                    id_of_a_site = dataProv.getSitesId(site);
                    servicesString = item.split(":")[3];
                    services = List.of(servicesString.split(","));

                    row.setSiteName(site);
                    row.setServices(services);
                    row.setSiteId(id_of_a_site);
                    row.setOperation(operation);
                    if (id_of_a_site.length() > 0) {
                        System.out.println(row.toString());
                        rows.add(row);
                        // else, this site does not exist
                    }
                }else{
                    System.out.println("Malformed data");
                }
            }
        }

        rds.setRows(rows);

        return 1;
    }

}
