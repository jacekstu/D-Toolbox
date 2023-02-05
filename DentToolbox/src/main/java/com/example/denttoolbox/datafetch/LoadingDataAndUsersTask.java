package com.example.denttoolbox.datafetch;

import com.example.denttoolbox.entity.RowDataSingleton;
import com.example.denttoolbox.entity.RowDataUsersSingleton;
import com.example.denttoolbox.entity.SingleRow;
import com.example.denttoolbox.entity.SingleRowSiteUsers;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class LoadingDataAndUsersTask extends Task<Integer> {

    public DataProviderService dps = new DataProviderService();
    public List<String> items = new ArrayList<>();

    public ArrayList<SingleRowSiteUsers> rows = new ArrayList<>();
    public RowDataUsersSingleton rds = RowDataUsersSingleton.getInstance();

    public LoadingDataAndUsersTask(List<String> items ){
        this.items = items;
    }

    @Override
    protected Integer call() throws Exception {
        int tick = 1;
        int updateVal = 1;

        String operation, site, id_of_a_site, servicesString;
        List<String> services = new ArrayList<>();
        List<String> userIds = new ArrayList<>();

        int length_of_items = items.size();

        for (String item : items){

            if (item.length() > 0){
                String [] payload_length = item.split(":");
                if (payload_length.length == 4) {
                    site = item.split(":")[2];

                    id_of_a_site = dps.getSitesId(site);

                     if(id_of_a_site.equals("")){
                         tick -= 1;
                         //updateValue(tick);
                         length_of_items -= 1;
                         System.out.println("Length of items in no such site: " + length_of_items);
                         // Add this to the error body
                     }else {

                         userIds = dps.getUsersBelongingToASite(id_of_a_site);
                         for (String id : userIds) {
                             SingleRowSiteUsers row = new SingleRowSiteUsers();
                             operation = item.split(":")[0];
                             id_of_a_site = dps.getSitesId(site);
                             servicesString = item.split(":")[3];
                             services = List.of(servicesString.split(","));

                             row.setOperation(operation);
                             row.setSiteId(id_of_a_site);
                             row.setServices(services);
                             row.setSiteName(site);
                             row.setSiteUserId(id);

                             updateVal *= services.size();
                             System.out.println("Tyle teraz rowna sie tick::::: " + tick);

                             if (id_of_a_site.length() > 0) {
                                 rows.add(row);
                                 tick += updateVal;
                                 System.out.println(row);
                                 updateValue(tick);
                                 updateVal = 1;
                             }
                         }
                         length_of_items -= 1;

                         System.out.println("Length of items in no Ogolme: " + length_of_items);

                     }
                    if (length_of_items == 0) {
                        updateValue(9999);
                        tick = 9999;
                    }
                }
                else{
                    System.out.println("Malformed data row");
                    tick -= 1;
                    updateValue(tick);
                    length_of_items -= 1;
                    System.out.println("Length of items in malformed data: " + length_of_items);
                }
            }else{
                length_of_items -= 1;
                System.out.println("Length of items Calkowity else: " + length_of_items);
            }

        }
        rds.setRows(rows);

        System.out.println(tick + "tick returned");
        return tick;
    }
}
