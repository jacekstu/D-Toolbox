package com.example.denttoolbox;

import com.example.denttoolbox.datafetch.*;
import com.example.denttoolbox.entity.*;
import com.example.denttoolbox.exceptions.NoNumberOfEntitiesToCreateException;
import com.example.denttoolbox.exceptions.NoclientNameException;
import com.example.denttoolbox.exceptions.SingleOptionException;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


public class DashboardController implements Initializable {

    @FXML
    private StackPane stackPane_loadingBarHolder;

    // This Anchor will hold the stack pane that will hold the loading bar
    @FXML
    private AnchorPane createClientsLoadingRingAnchor;
    @FXML
    private StackPane stakPaneRingHolderEntities;

    @FXML
    private AnchorPane anchorPane_loading;

    PositionSingleton ps = PositionSingleton.getInstance();
    @FXML
    private AnchorPane ancohor_ErrorsView;

    @FXML
    private Label label_numberErrors;




    public DashboardController(){

    }

    //double x = 0;
    //double y = 0;

    double x = ps.getX();
    double y = ps.getY();


    @FXML
    private AnchorPane anchorPaneStats;

    @FXML
    private Label labelAddServiceNumber;

    @FXML
    private Label labelDeleteServiceNumber;
    @FXML
    private AnchorPane form_serviceModifier;

    @FXML
    private AnchorPane form_entityCreator;

    @FXML
    private Button btn_minimize;


    @FXML
    private Button btn_loadFile;

    @FXML
    private Button btn_home;

    @FXML
    private ImageView img_loading;

    @FXML
    private Label lbl_username;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_serviceModifier;

    @FXML
    private Button btn_entitlementModifier;
    @FXML
    private Button btn_siteServerConfigCreator;

    @FXML
    private Button btn_entityCreator;

    @FXML
    private Button btn_serviceDefCreator;

    @FXML
    private Button btn_execute;
    @FXML
    private Button btn_proceed;


    @FXML
    private Button test;
    @FXML
    private Label lbl_number_of_clients;

    @FXML
    private Label labelek;

    @FXML
    private Label lbl_number_of_sites;

    @FXML
    private Label lbl_number_of_siteUsers;

    @FXML
    private Label lbl_welcome_msg_username;
    @FXML
    private Label lbl_pending_changes_message;

    @FXML
    private Label lbl_pendoingChangesHeader;

    @FXML
    private Label lbl_pendingChangesRecommendMessage;

    @FXML
    private Label lbl_numberPendingChanges;

    @FXML
    private Label lbl_lastClient;

    @FXML
    private Label lbl_lastSite;

    @FXML
    private Label lbl_lastUser;

    @FXML
    private Label lbl_current_clients;

    @FXML
    private Label lbl_current_sites;

    @FXML
    private Label lbl_current_csiteusers;

    @FXML
    private Label lbl_current_entsets;

    @FXML
    private ComboBox<String> ComboBox_sitesSelectEndpoint;
    private String[] endpoints = {"Sites", "Users"};

    // A Combobox from which the user can choose which endpoint
    // to create
    @FXML
    private ComboBox<String> combobox_endpoints;
    private String[] create_endpoints = {"Clients","Sites", "SiteUsers", "EntitlementSets"};

    private String inactiveButtonStyle = "-fx-background-color:transparent";
    private String activeEntityCreated = " -fx-text-fill:#FF0043;";
    private String inactiveEntityCreated = " -fx-text-fill:#4fd0e4;";
    private String activeButtonStyle = "-fx-background-color:linear-gradient(to right, #F80041, #8E0DA3)";

    private String clientErrorsStyles = "-fx-background-color:red; -fx-background-radius:10px; -fx-opacity:0.74;";

    @FXML
    private AnchorPane anchorPaneModifyService;

    @FXML
    private AnchorPane form_home;
    public CredentialsSingleton data = CredentialsSingleton.getInstance();
    public RowDataSingleton rds = RowDataSingleton.getInstance();
    public RowDataUsersSingleton rdus = RowDataUsersSingleton.getInstance();

    public DataProviderService dataProv = new DataProviderService();
    public InventorySingleton inventoryData = InventorySingleton.getInstance();

    // ArrayList for storing SingleRow objects
    public ArrayList<SingleRow> rows = new ArrayList<>();

    public List<String> services_data = new ArrayList<>();

    @FXML
    private AnchorPane anchorPane_servicesLoad;

    public RingProgressIndicator rpi = new RingProgressIndicator();
    // Another loading ring, this time when creating entities
    public RingProgressIndicator createEntititesRing = new RingProgressIndicator();

    public String endpointSelected;

    @FXML
    private AnchorPane anchorPane_operationsCount;

    @FXML
    private Label lbl_operationCount;

    @FXML
    private Label lbl_operCountString;

    public ErrorSingleton errorSingleton = ErrorSingleton.getInstance();

    @FXML
    private Button btn_downloadErrors;

    @FXML
    private Button btn_create_clients;

    // TABLE ELEMENTS
    @FXML
    private TableView<DistributeRow> table_services;

    @FXML
    private TableColumn<DistributeRow, String> col_type;

    @FXML
    private TableColumn<DistributeRow, String> col_name;

    @FXML
    private TableColumn<DistributeRow, String> col_action;

    @FXML
    private TableColumn<DistributeRow, String> col_user;

    @FXML
    private TableColumn<DistributeRow, String> col_hostname;
    // TABLE ELEMENTS

    public ObservableList<DistributeRow> list = FXCollections.observableArrayList();

    @FXML
    private TextField filterField;

    Workbook workbook;
    @FXML
    private Button btn_excelDownload;

    // the main anchor showing the cube and start populating...
    @FXML
    private AnchorPane anchor_no_entity;

    @FXML
    private AnchorPane anchor_create_client;
    @FXML
    private AnchorPane anchor_create_site;
    @FXML
    private AnchorPane anchor_create_siteuser;

    // List to hold distribute data to write into excel
    public ArrayList<DistributeRow> dataForExcelFile = new ArrayList<>();

    // CLIENT RELATED FIELDS
    @FXML
    private TextField txt_fld_number_of_clients_to_create;

    @FXML
    private TextField filed_clientName;
    @FXML
    private Label no_client_name_error_label;

    @FXML
    private CheckBox checkBox_randomClientName;

    @FXML
    private CheckBox checkBoxSecondary;


    @FXML
    private TextField fieldSecondaryName;

    @FXML
    private TextField fieldAddressLine1;

    @FXML
    private TextField fieldAddressLine2;

    @FXML
    private TextField fieldAddressLine3;

    @FXML
    private TextField fieldCity;

    @FXML
    private TextField fieldStateProvince;

    @FXML
    private TextField fieldPostCodeZipcode;

    @FXML
    private TextField fieldCountry;
    // CLIENT RELATED FIELDS



    public void hide_error_button_client_create(){

        no_client_name_error_label.setVisible(false);

    }
    @FXML
    void saveErrorsFile(MouseEvent event) {

        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("errors.txt");
        fileChooser.getExtensionFilters().add(ex1);
        fileChooser.setTitle("Saving Errors file - please select location");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null){
            saveErrors(file);
        }
    }

    @FXML
    void saveExcelFile(MouseEvent event) throws IOException {
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("Text Files", "*.xls");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("service_distributes.xls");
        fileChooser.getExtensionFilters().add(ex1);
        fileChooser.setTitle("Saving distributes excel file - please select location");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null){
            generateExcelFile(file);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ComboBox_sitesSelectEndpoint.getItems().addAll(endpoints);
        ComboBox_sitesSelectEndpoint.setDisable(true);

        // populate combobox with options
        combobox_endpoints.getItems().addAll(create_endpoints);

        rpi.setRingWidth(25);
        rpi.makeIndeterminate();
        stackPane_loadingBarHolder.getChildren().add(rpi);

        // Another loading ring (bar), this one to be used
        // denoting progress in creating entities
        createEntititesRing.setRingWidth(35);
        createEntititesRing.setInnerCircleRadius(45);
        createEntititesRing.makeIndeterminate();
        stakPaneRingHolderEntities.getChildren().add(createEntititesRing);


        lbl_username.setText(data.getUsername());
        lbl_welcome_msg_username.setText(data.getUsername());

        dataProv.getEntities();
        btn_execute.setDisable(true);

        lbl_number_of_clients.setText(inventoryData.getClientsNumber());
        lbl_current_clients.setText(inventoryData.getClientsNumber());

        lbl_number_of_sites.setText(inventoryData.getSitesNumber());
        lbl_current_sites.setText(inventoryData.getSitesNumber());

        lbl_number_of_siteUsers.setText(inventoryData.getSiteUsersNumber());
        lbl_current_csiteusers.setText(inventoryData.getSiteUsersNumber());

        lbl_lastSite.setText(inventoryData.getLastSiteName());
        lbl_lastClient.setText(inventoryData.getLastClientName());
        lbl_lastUser.setText(inventoryData.getLastSiteUserName());

        if (inventoryData.getNumberOfPendingDistributes() > 0){
            lbl_pendoingChangesHeader.setVisible(true);
            lbl_pendingChangesRecommendMessage.setVisible(true);
            lbl_pending_changes_message.setVisible(false);

            lbl_numberPendingChanges.setText(String.valueOf((inventoryData.getNumberOfPendingDistributes() - 1)));
        }else{
            lbl_pendoingChangesHeader.setVisible(false);
            lbl_pendingChangesRecommendMessage.setVisible(false);
            lbl_pending_changes_message.setVisible(true);
            lbl_pending_changes_message.setText("There are no pending changes.");

            lbl_numberPendingChanges.setVisible(false);
        }

        btn_execute.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                // When re-loading a file make sure
                // The loading bar is no longer displayed
                stackPane_loadingBarHolder.setVisible(false);

                anchorPaneModifyService.setVisible(false);
                img_loading.setVisible(true);
                labelek.setVisible(true);
                labelek.setText("Calculating...");

                System.out.println("Pressing the execute button");
                RotateTransition rotate = new RotateTransition();
                rotate.setNode(img_loading);
                rotate.setByAngle(360);
                rotate.setInterpolator(Interpolator.LINEAR);
                rotate.setDuration(Duration.millis(900));
                rotate.setCycleCount(TranslateTransition.INDEFINITE);
                rotate.play();

                // Create and populate a list of rows
                if (endpointSelected.equals("Sites")){
                    getRowsOfData(services_data);
                } else if (endpointSelected.equals("Users")){
                    getRowsOfDataAndUsers(services_data);
                }
            }
        });

        btn_proceed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // hide the button itself and the pane
                anchorPaneModifyService.setVisible(false);
                anchorPaneStats.setVisible(false);
                // disable the load service file button
                btn_loadFile.setDisable(true);

                // show the loading bar
                stackPane_loadingBarHolder.setVisible(true);
                // set the progress to 0%
                rpi.setProgress(0);
                assignUnassignService();

                anchorPane_operationsCount.setVisible(false);
            }
        });

        // CREATE CLIENTS BUTTON HANDLER
        btn_create_clients.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try{
                    if (filed_clientName.getText().length() == 0 && checkBox_randomClientName.isSelected() == false){
                        throw new NoclientNameException("501", "No client name provided");
                    }
                    if (txt_fld_number_of_clients_to_create.getText().length() == 0){
                        throw new NoNumberOfEntitiesToCreateException("502", "No number of entitties to create provided");
                    }else{
                        Integer.parseInt(txt_fld_number_of_clients_to_create.getText());
                    }

                    // create client singleton class
                    ClientSingletonClass csc = ClientSingletonClass.getInstance();

                    if (filed_clientName.getText().length() > 0 && checkBox_randomClientName.isSelected() == true){
                        throw new SingleOptionException("503", "Either provide value for client name parameter or select randomize option");
                    }else if(filed_clientName.getText().length() == 0 && checkBox_randomClientName.isSelected() == true){
                        csc.setClientName(setRandomName(255));
                    }else{
                        csc.setClientName(filed_clientName.getText());
                    }

                    if (fieldSecondaryName.getText().length() > 0 && checkBoxSecondary.isSelected() == true){
                        throw new SingleOptionException("503", "Either provide value for secondary name parameter or select randomize option");
                    }else if(fieldSecondaryName.getText().length() == 0 && checkBoxSecondary.isSelected() == true){
                        csc.setSecondaryName(setRandomName(255));
                    }
                    else{
                        csc.setSecondaryName(fieldSecondaryName.getText());
                    }

                    csc.setAddress1(fieldAddressLine1.getText());
                    csc.setAddress2(fieldAddressLine2.getText());
                    csc.setAddress3(fieldAddressLine3.getText());
                    csc.setCity(fieldCity.getText());
                    csc.setPostCode(fieldPostCodeZipcode.getText());
                    csc.setStateProvince(fieldStateProvince.getText());
                    csc.setCountry(fieldCountry.getText());
                    //csc.setVendorReportingId(fi);


                    // Hide the anchors
                    // - The  initial one, when no entity slected from the drop down window
                    // then hide the create client anchor
                    anchor_create_client.setVisible(false);
                    anchor_no_entity.setVisible(false);
                    createClientsLoadingRingAnchor.setVisible(true);
                    createEntititesRing.setProgress(0);
                    runCreateEntityTask("client",
                            Integer.parseInt(txt_fld_number_of_clients_to_create.getText()));
                } catch(NoclientNameException e){
                    no_client_name_error_label.setText("Please provide a value for the client name field or tick the randomize option");
                    no_client_name_error_label.setStyle(clientErrorsStyles);
                    no_client_name_error_label.setVisible(true);
                } catch (NoNumberOfEntitiesToCreateException e) {
                    no_client_name_error_label.setText("Please provide the number of clients to create");
                    no_client_name_error_label.setStyle(clientErrorsStyles);
                    no_client_name_error_label.setVisible(true);
                } catch (NumberFormatException e){
                    System.out.println("This is not an integer");
                    no_client_name_error_label.setText("Please provide a valid integer value");
                    no_client_name_error_label.setStyle(clientErrorsStyles);
                    no_client_name_error_label.setVisible(true);
                } catch (SingleOptionException e) {
                    no_client_name_error_label.setText(e.getMessage());
                    no_client_name_error_label.setStyle(clientErrorsStyles);
                    no_client_name_error_label.setVisible(true);
                }

            }
        });
    }

    public void clean_client_create_fields(){
        filed_clientName.setText("");
        fieldSecondaryName.setText("");
        fieldAddressLine1.setText("");
        fieldAddressLine2.setText("");
        fieldAddressLine3.setText("");
        fieldCity.setText("");
        fieldPostCodeZipcode.setText("");
        fieldStateProvince.setText("");
        fieldCountry.setText("");
    }

    private void runCreateEntityTask(String entity, int numberOfEntities){

        CreateEntityTask cet = new CreateEntityTask(numberOfEntities, entity);
        cet.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                System.out.println(t1 + "<<< this is t1");
                if (t1 == numberOfEntities){
                    createClientsLoadingRingAnchor.setVisible(false);
                    clean_client_create_fields();
                    anchor_create_client.setVisible(true);
                    // update the inventory
                    // run the get entities function, that runs the get posts for clients/sites and user
                    dataProv.getEntities();
                    // update all the lables showing the current number of clients
                    lbl_current_clients.setText(inventoryData.getClientsNumber());
                    lbl_number_of_clients.setText(inventoryData.getClientsNumber());
                    lbl_numberPendingChanges.setText(String.valueOf((inventoryData.getNumberOfPendingDistributes() - 1)));
                    lbl_lastClient.setText(inventoryData.getLastClientName());
                    // Reset the number of clients to create field
                    txt_fld_number_of_clients_to_create.setText("");

                    // Reset the check boxes
                    checkBox_randomClientName.selectedProperty().setValue(false);
                    checkBoxSecondary.selectedProperty().setValue(false);


                }else{
                    double percent = ( (double )t1 / (double )numberOfEntities) * 100;
                    createEntititesRing.setProgress((int) Math.round(percent));
                }

            }
        });

        Thread th = new Thread(cet);
        th.setDaemon(true);
        th.start();

    }

    public void saveErrors(File file){
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(errorSingleton.listErrors());
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectEndpoint(){
        endpointSelected = ComboBox_sitesSelectEndpoint.getValue();
        if (endpointSelected != null){
            btn_execute.setDisable(false);
        }
    }

    public void selectEndpointToCreate(){
        endpointSelected = combobox_endpoints.getValue();
        if (endpointSelected == "Clients"){
            anchor_no_entity.setVisible(false);
            anchor_create_site.setVisible(false);
            anchor_create_siteuser.setVisible(false);
            anchor_create_client.setVisible(true);

            // Change the font size of the label with the number of entities
            lbl_current_clients.setStyle(activeEntityCreated);
            lbl_current_sites.setStyle(inactiveEntityCreated);
            lbl_current_csiteusers.setStyle(inactiveEntityCreated);

        }else if (endpointSelected == "Sites"){
            anchor_no_entity.setVisible(false);
            anchor_create_site.setVisible(true);
            anchor_create_siteuser.setVisible(false);
            anchor_create_client.setVisible(false);

            // Change the font size of the label with the number of entities
            lbl_current_clients.setStyle(inactiveEntityCreated);
            lbl_current_sites.setStyle(activeEntityCreated);
            lbl_current_csiteusers.setStyle(inactiveEntityCreated);

            // Hide the errors from the other views
            no_client_name_error_label.setVisible(false);

        }else if (endpointSelected == "SiteUsers"){
            anchor_no_entity.setVisible(false);
            anchor_create_site.setVisible(false);
            anchor_create_siteuser.setVisible(true);
            anchor_create_client.setVisible(false);

            // Change the font size of the label with the number of entities
            lbl_current_clients.setStyle(inactiveEntityCreated);
            lbl_current_sites.setStyle(inactiveEntityCreated);
            lbl_current_csiteusers.setStyle(activeEntityCreated);

            // Hide the errors from the other views
            no_client_name_error_label.setVisible(false);

        }else if(endpointSelected == "EntitlemetsSets"){
            anchor_no_entity.setVisible(false);
            anchor_create_site.setVisible(false);
            anchor_create_siteuser.setVisible(false);
            anchor_create_client.setVisible(false);

            // Change the font size of the label with the number of entities
            lbl_current_clients.setStyle(inactiveEntityCreated);
            lbl_current_sites.setStyle(inactiveEntityCreated);
            lbl_current_csiteusers.setStyle(activeEntityCreated);

            // Hide the errors from the other views
            no_client_name_error_label.setVisible(false);
        }
    }

    public void elementsSetupBeforeAssigning(){

        anchorPaneModifyService.setVisible(true);
        btn_execute.setDisable(true);
        anchorPaneStats.setVisible(true);
        ComboBox_sitesSelectEndpoint.setDisable(true);

        img_loading.setVisible(false);
        labelek.setVisible(false);
        anchorPane_operationsCount.setVisible(false);
        anchorPaneModifyService.setVisible(false);

        // hide the operations counter
        anchorPane_operationsCount.setVisible(false);
    }

    public void getRowsOfDataAndUsers(List<String> items){

        LoadingDataAndUsersTask lut = new LoadingDataAndUsersTask(items);
        lut.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {

                switch(t1){
                    case 9999 -> setFinishedLoadingUsersData();
                    default ->  setLoadingUserData(t1);
                }
            }
        });

        Thread th = new Thread(lut);
        th.setDaemon(true);
        th.start();
    }

    public void setLoadingUserData(int t1){

        labelek.setVisible(false);
        anchorPane_operationsCount.setVisible(true);
        lbl_operationCount.setText(String.valueOf(t1));
    }
    public void setFinishedLoadingUsersData(){

        anchorPane_operationsCount.setVisible(false);

        // Set the UI elements
        elementsSetupBeforeAssigning();

        int [] stats = rdus.getStats();

        String addServices = stats[0] > 1 ? " services" : " service";
        String deleteServices = stats[1] > 1 ? " services" : " service";

        labelAddServiceNumber.setText("Add " +  stats[0] + addServices);
        labelDeleteServiceNumber.setText("Delete " +  stats[1] + deleteServices);
    }
    public void getRowsOfData(List<String> items){

        LoadingDataTask lt = new LoadingDataTask(items);
        lt.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                if (t1 == 1){
                    // Set the UI elements
                    elementsSetupBeforeAssigning();

                    int [] stats = rds.getStats();

                    String addServices = stats[0] > 1 ? " services" : " service";
                    String deleteServices = stats[1] > 1 ? " services" : " service";

                    labelAddServiceNumber.setText("Add " +  stats[0] + addServices);
                    labelDeleteServiceNumber.setText("Delete " +  stats[1] + deleteServices);
                }
            }
        });

        Thread th = new Thread(lt);
        th.setDaemon(true);
        th.start();
    }

    public void assignUnassignService(){

        PostServiceTask pst = new PostServiceTask(endpointSelected);
        pst.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                ObservableList<DistributeRow> rowsToPopulate = FXCollections.observableArrayList();
                if (endpointSelected.equals("Sites")) {
                    int totalNumberOfOperations = rds.getTotalNumberOfOperations();
                    double percent = ( (double )t1 / (double )totalNumberOfOperations) * 100;
                    rpi.setProgress((int) Math.round(percent));
                    // Observalble list fors soting distributes

                    if (t1  == totalNumberOfOperations){

                        /// Populate Table with Distribute Data
                        try {
                            rowsToPopulate = populateDistributeTable(dataProv.getDistributes());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        col_name.setCellValueFactory(new PropertyValueFactory<DistributeRow, String>("name"));
                        col_action.setCellValueFactory(new PropertyValueFactory<DistributeRow, String>("action"));
                        col_hostname.setCellValueFactory(new PropertyValueFactory<DistributeRow, String>("hostname"));
                        col_type.setCellValueFactory(new PropertyValueFactory<DistributeRow, String>("type"));
                        col_user.setCellValueFactory(new PropertyValueFactory<DistributeRow, String>("user"));

                        FilteredList<DistributeRow> filteredData = new FilteredList<>(rowsToPopulate, b -> true);

                        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                            filteredData.setPredicate(dataRow -> {
                                if (newValue == null || newValue.isEmpty()){
                                    return true;
                                }

                                String lowerCaseFilter = newValue.toLowerCase();

                                if (dataRow.getType().toLowerCase().indexOf(lowerCaseFilter) != -1){
                                    return true; //filter matches type
                                } else if (dataRow.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                                    return true; // filter macthes name
                                } else if (dataRow.getAction().toLowerCase().indexOf(lowerCaseFilter) != -1){
                                    return true;
                                } else if (dataRow.getUser().toLowerCase().indexOf(lowerCaseFilter) != -1){
                                    return true;
                                } else if (dataRow.getHostname().toLowerCase().indexOf(lowerCaseFilter) != -1){
                                    return true;
                                }
                                else
                                    return false;
                            });
                        });

                        SortedList<DistributeRow> sortedData = new SortedList<>(filteredData);

                        sortedData.comparatorProperty().bind(table_services.comparatorProperty());
                        table_services.setItems(sortedData);

                        // update the number of pedning distributes in the home page
                        dataProv.getEntities();
                        lbl_numberPendingChanges.setText(String.valueOf((inventoryData.getNumberOfPendingDistributes() - 1)));


                        btn_loadFile.setDisable(false);

                        ComboBox_sitesSelectEndpoint.setDisable(true);
                        ComboBox_sitesSelectEndpoint.setValue(null);
                        // display the excel download button
                        btn_excelDownload.setVisible(true);

                        // Clear the objects
                        rds.setRows(null);

                        if (errorSingleton.getSize() > 0){
                            errorSingleton.listErrors();
                            label_numberErrors.setText(String.valueOf(errorSingleton.getSize()));
                            ancohor_ErrorsView.setVisible(true);
                        }else{
                            labelek.setVisible(true);
                            labelek.setText("Done!");
                        }
                    }
                } else if(endpointSelected.equals("Users")){
                    int totalNumberOfOperations = rdus.getTotalNumberOfOperations();
                    double percent = ((double) t1 / (double) totalNumberOfOperations) * 100;
                    rpi.setProgress((int) Math.round(percent));

                    if (t1  == totalNumberOfOperations){

                        /// Populate Table with Distribute Data
                        try {
                            rowsToPopulate = populateDistributeTable(dataProv.getDistributes());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        col_name.setCellValueFactory(new PropertyValueFactory<DistributeRow, String>("name"));
                        col_action.setCellValueFactory(new PropertyValueFactory<DistributeRow, String>("action"));
                        col_hostname.setCellValueFactory(new PropertyValueFactory<DistributeRow, String>("hostname"));
                        col_type.setCellValueFactory(new PropertyValueFactory<DistributeRow, String>("type"));
                        col_user.setCellValueFactory(new PropertyValueFactory<DistributeRow, String>("user"));

                        // SET FILTERED LIST
                        FilteredList<DistributeRow> filteredData = new FilteredList<>(rowsToPopulate, b -> true);

                        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                            filteredData.setPredicate(dataRow -> {
                                if (newValue == null || newValue.isEmpty()){
                                    return true;
                                }

                                String lowerCaseFilter = newValue.toLowerCase();

                                if (dataRow.getType().toLowerCase().indexOf(lowerCaseFilter) != -1){
                                    return true; //filter matches type
                                } else if (dataRow.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                                    return true; // filter macthes name
                                } else if (dataRow.getAction().toLowerCase().indexOf(lowerCaseFilter) != -1){
                                    return true;
                                } else if (dataRow.getUser().toLowerCase().indexOf(lowerCaseFilter) != -1){
                                    return true;
                                } else if (dataRow.getHostname().toLowerCase().indexOf(lowerCaseFilter) != -1){
                                    return true;
                                }else
                                    return false;
                            });
                        });

                        SortedList<DistributeRow> sortedData = new SortedList<>(filteredData);

                        sortedData.comparatorProperty().bind(table_services.comparatorProperty());
                        table_services.setItems(sortedData);
                        // FINSSHED SETTING FILTERED LIST


                        // update the number of pedning distributes in the home page
                        dataProv.getEntities();
                        lbl_numberPendingChanges.setText(String.valueOf(inventoryData.getNumberOfPendingDistributes()));

                        btn_loadFile.setDisable(false);

                        ComboBox_sitesSelectEndpoint.setDisable(true);
                        ComboBox_sitesSelectEndpoint.setValue(null);

                        // display the excel download button
                        btn_excelDownload.setVisible(true);



                        // Clear the objects
                        rdus.setRows(null);
                        if (errorSingleton.getSize() > 0){
                            errorSingleton.listErrors();
                            label_numberErrors.setText(String.valueOf(errorSingleton.getSize()));
                            ancohor_ErrorsView.setVisible(true);
                        }else{
                            labelek.setVisible(true);
                            labelek.setText("Done!");
                        }
                    }
                }
            }
        });

        Thread th = new Thread(pst);
        th.setDaemon(true);
        th.start();
    }

    public void generateExcelFile(File file) throws IOException {

        Sheet sh = null;
        ArrayList<DistributeRow> excelData = new ArrayList<>();
        try{
            workbook = new HSSFWorkbook();
            sh = workbook.createSheet("service_distributes");
            // Create top row with column headings
            String[] columnHeadings = {"Type", "Name", "Action", "User", "Hostname"};
            // change the color of the header row
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short)12);
            headerFont.setColor(IndexedColors.DARK_GREEN.index);
            // create a CellStyle
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
            // create the header row
            Row headerRow = sh.createRow(0);
            // Iterate over the column headings to create columns
            for(int i =0; i<columnHeadings.length; i++){
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnHeadings[i]);
                cell.setCellStyle(headerStyle);
            }

            // populate excel?
            int rowNum = 1;
            for (DistributeRow i : dataForExcelFile){
                Row row = sh.createRow(rowNum++);
                row.createCell(0).setCellValue(i.getType());
                row.createCell(1).setCellValue(i.getName());
                row.createCell(2).setCellValue(i.getAction());
                row.createCell(3).setCellValue(i.getUser());
                row.createCell(4).setCellValue(i.getHostname());
            }
            // Autosize columns
            for (int i=0; i< 5;i++){
                sh.autoSizeColumn(i);
            }

            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<DistributeRow> populateDistributeTable(String body) throws IOException {


        String [] data = body.split("\"type\"");
        String [] dataName = body.split("\"name\"");
        String [] dataAction = body.split("\"action\"");
        String [] dataUser = body.split("\"loginName\"");
        String [] dataHost = body.split("\"hostName\"");

        for(int i = 1; i < data.length; i++){

            String type = data[i].split("\",")[0];
            type = type.split(" : \"")[1];

            String action = dataAction[i].split("\",")[0];
            action = action.split(" : \"")[1];

            String name = dataName[i].split("\",")[0];
            name = name.split(" : \"")[1];

            String user = dataUser[i].split("\",")[0];
            user = user.split(" : \"")[1];

            String hostname = dataHost[i].split("\",")[0];
            hostname = hostname.split(" : \"")[1];
            hostname = hostname.split("\"")[0];

            list.add(new DistributeRow(type,name,action,user,hostname));
            dataForExcelFile.add(new DistributeRow(type,name,action,user,hostname));
        }

        return list;
    }

    public void switchForm(ActionEvent event){

        if (event.getSource() == btn_home ){
            // show/hide forms
            form_home.setVisible(true);
            form_serviceModifier.setVisible(false);
            form_entityCreator.setVisible(false);

            // change button's background
            btn_home.setStyle(activeButtonStyle);
            btn_serviceModifier.setStyle(inactiveButtonStyle);
            btn_entitlementModifier.setStyle(inactiveButtonStyle);
            btn_entityCreator.setStyle(inactiveButtonStyle);
            btn_serviceDefCreator.setStyle(inactiveButtonStyle);
            btn_siteServerConfigCreator.setStyle(inactiveButtonStyle);
        }else if(event.getSource() == btn_serviceModifier){

            // show/hide forms
            form_home.setVisible(false);
            form_serviceModifier.setVisible(true);
            form_entityCreator.setVisible(false);

            // change button's background
            btn_home.setStyle(inactiveButtonStyle);
            btn_serviceModifier.setStyle(activeButtonStyle);
            btn_entitlementModifier.setStyle(inactiveButtonStyle);
            btn_entityCreator.setStyle(inactiveButtonStyle);
            btn_serviceDefCreator.setStyle(inactiveButtonStyle);
            btn_siteServerConfigCreator.setStyle(inactiveButtonStyle);
        }else if(event.getSource() == btn_entitlementModifier){
            // show/hide forms
            form_home.setVisible(false);
            form_serviceModifier.setVisible(false);
            form_entityCreator.setVisible(false);

            // change button's background
            btn_home.setStyle(inactiveButtonStyle);
            btn_serviceModifier.setStyle(inactiveButtonStyle);
            btn_entitlementModifier.setStyle(activeButtonStyle);
            btn_entityCreator.setStyle(inactiveButtonStyle);
            btn_serviceDefCreator.setStyle(inactiveButtonStyle);
            btn_siteServerConfigCreator.setStyle(inactiveButtonStyle);
        }else if(event.getSource() == btn_entityCreator){
            // show/hide forms
            form_home.setVisible(false);
            form_serviceModifier.setVisible(false);
            form_entityCreator.setVisible(true);

            // change button's background
            btn_home.setStyle(inactiveButtonStyle);
            btn_serviceModifier.setStyle(inactiveButtonStyle);
            btn_entitlementModifier.setStyle(inactiveButtonStyle);
            btn_entityCreator.setStyle(activeButtonStyle);
            btn_serviceDefCreator.setStyle(inactiveButtonStyle);
            btn_siteServerConfigCreator.setStyle(inactiveButtonStyle);
        }else if(event.getSource() == btn_serviceDefCreator){
            // show/hide forms
            form_home.setVisible(false);
            form_serviceModifier.setVisible(false);
            form_entityCreator.setVisible(false);

            // change button's background
            btn_home.setStyle(inactiveButtonStyle);
            btn_serviceModifier.setStyle(inactiveButtonStyle);
            btn_entitlementModifier.setStyle(inactiveButtonStyle);
            btn_entityCreator.setStyle(inactiveButtonStyle);
            btn_serviceDefCreator.setStyle(activeButtonStyle);
            btn_siteServerConfigCreator.setStyle(inactiveButtonStyle);
        }else if (event.getSource() == btn_siteServerConfigCreator){
            // show/hide forms
            form_home.setVisible(false);
            form_serviceModifier.setVisible(false);
            form_entityCreator.setVisible(false);

            // change button's background
            btn_siteServerConfigCreator.setStyle(activeButtonStyle);
            btn_home.setStyle(inactiveButtonStyle);
            btn_serviceModifier.setStyle(inactiveButtonStyle);
            btn_entitlementModifier.setStyle(inactiveButtonStyle);
            btn_entityCreator.setStyle(inactiveButtonStyle);
            btn_serviceDefCreator.setStyle(inactiveButtonStyle);
        }
    }
    public void minimize(){
        Stage stage = (Stage)btn_minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    public void close(){
        System.exit(1);
    }

    public void clean_login_cache(){

        data.setHostname("");
        data.setPortNumber("");
        data.setUsername("");
        data.setPassword("");
        data.setResponseCode(-1);
    }

    public void test(){
        dataProv.getDistributes();
    }

    public void logout() throws IOException {
        // reset the data in the CredentialsSingleton class
        clean_login_cache();
        btn_logout.getScene().getWindow().hide();

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        scene.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

            ps.setX(event.getScreenX() - x);
            ps.setY(event.getScreenY() - y);

            stage.setOpacity(.8);
        });

        scene.setOnMouseReleased((MouseEvent event) -> {
            stage.setOpacity(1);
        });

        stage.setX(ps.getX());
        stage.setY(ps.getY());

        stage.initStyle(StageStyle.TRANSPARENT);
        Image image = new Image("D:\\Sprinkbut\\Zajaveczka\\DentToolbox\\src\\main\\java\\com\\example\\denttoolbox\\logodent.png");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
    }

    public void selectFile(){

        // Empty the list that will be used for populating the excel file
        dataForExcelFile.clear();
        // Also hide the excel download button
        btn_excelDownload.setVisible(false);

        // delete contetns from the distribute list
        if (list.size() > 0) {
            list.clear();
        }
        // Hide errors stats and flush the erro Arraylist
        ancohor_ErrorsView.setVisible(false);
        errorSingleton.flushErrors();
        // If services data already loaded, and the user changes their minds
        // Remove interface elements describing the number of services to be added/deleted
        anchorPaneStats.setVisible(false);
        ComboBox_sitesSelectEndpoint.setValue(null);
        anchorPane_operationsCount.setVisible(false);

        // when clicking on the select file, we need to hide the loading bar
        // and show the pane with the arrow/paper plane
        stackPane_loadingBarHolder.setVisible(false);
        anchorPaneModifyService.setVisible(true);
        labelek.setVisible(false);
        ComboBox_sitesSelectEndpoint.setPromptText("Select endpoint");

        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("Text Files", "*.txt");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Please select the file containing serivces to add/delete");
        fileChooser.getExtensionFilters().add(ex1);
        File selectedFile = fileChooser.showOpenDialog(btn_home.getScene().getWindow());

        if (selectedFile != null) {
            String filePath = selectedFile.getPath();
            services_data = getDataFromFile(filePath);

            if (services_data.size() < 1){
                labelek.setVisible(true);
                labelek.setText("Empty file passed");
                ComboBox_sitesSelectEndpoint.setDisable(true);
                ComboBox_sitesSelectEndpoint.setPromptText("Select endpoint");
            }else{
                ComboBox_sitesSelectEndpoint.setDisable(false);
                ComboBox_sitesSelectEndpoint.setPromptText("Select endpoint");
            }
        }
    }

    public List<String> getDataFromFile(String filePath){

        List<String> servicesRows = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = reader.readLine())!= null){
                servicesRows.add(line);
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return servicesRows;
    }

    public String setRandomName(int numChars){

        Random rand = new Random();

        // get the length of the String
        int num = rand.nextInt(numChars - 1);

        String value = "";

        for (int i = 0 ; i < num; i++){
            value += randomChar();
        }
        return value;
    }

    public char randomChar(){
        Random rand = new Random();

        char [] letters = {'a', 'b', 'c', 'd', 'e','f', 'g', 'h', 'i',
                'j','k','l','m','n','o','p','r','s','t','u','w','x','y','z',
                'A', 'B', 'C', 'D', 'E','F', 'G', 'H', 'I',
                'J','K','L','M','N','O','P','R','S','T','U','W','X','Y','Z'
        };

        return letters[rand.nextInt(letters.length)];

    }

}
