package com.example.denttoolbox;

import com.example.denttoolbox.datafetch.CredentialsSingleton;
import com.example.denttoolbox.datafetch.DataProviderService;
import com.example.denttoolbox.datafetch.PositionSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import java.net.http.HttpClient;

public class HelloController implements Initializable {

    public HttpClient client;

    @FXML
    private Label lbl_currentDate;

    @FXML
    private Button btn_login;

    @FXML
    private TextField txtField_hostname;

    @FXML
    private TextField txtField_port;

    @FXML
    private TextField txtField_username;

    @FXML
    private PasswordField txtField_password;

    public CredentialsSingleton data;
    public PositionSingleton pSingleton = PositionSingleton.getInstance();



    @FXML
    private Button error_button;
    // Make the GUI draggable
    private double x = 0;
    private double y = 0;
    public void hide_error_button(){
        error_button.setVisible(false);
    }

    public void successfully_logged_in() throws IOException {

        // Hide the login window
        error_button.getScene().getWindow().hide();

        Parent root  = FXMLLoader.load(getClass().getResource("dashboard.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setX(pSingleton.getX());
        stage.setY(pSingleton.getY());

        Image image = new Image("D:\\Sprinkbut\\Zajaveczka\\DentToolbox\\src\\main\\java\\com\\example\\denttoolbox\\logodent.png");
        stage.getIcons().add(image);

        scene.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

            stage.setOpacity(.6);

            pSingleton.setX(event.getScreenX() - x);
            pSingleton.setY(event.getScreenY() - y);
        });

        scene.setOnMouseReleased((MouseEvent event) ->{
            stage.setOpacity(1);
        });

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();

    }

    public void login(){

        data = CredentialsSingleton.getInstance();

        if (txtField_hostname.getText().isEmpty() ||
                txtField_port.getText().isEmpty() ||
                txtField_username.getText().isEmpty() ||
                txtField_password.getText().isEmpty()){
            error_button.setText("Please provide all pieces of information");
            error_button.setVisible(true);
        }

        data.setHostname(txtField_hostname.getText());
        data.setPortNumber(txtField_port.getText());
        data.setUsername(txtField_username.getText());
        data.setPassword(txtField_password.getText());

        DataProviderService dts = new DataProviderService();

        int responseCode = dts.login_request();


        if (responseCode == 200){
            try{
                successfully_logged_in();
                //dts.getNumberOfEntities();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else if (responseCode == 401){
            error_button.setText("Please provide correct username and password");
            error_button.setVisible(true);
        }
        else if(responseCode == 100){
            error_button.setText("Port is out of range, provide valid port");
            error_button.setVisible(true);
        }
        else if(responseCode == 102){
            error_button.setText("Port is out of range, provide a valid port");
            error_button.setVisible(true);
        }
        else if(responseCode == 103){
            error_button.setText("Make sure you provided the correct hostname and port");
            error_button.setVisible(true);
        }
    }

    public void displayCurrentDate(){

        LocalDateTime now =LocalDateTime.now();
        lbl_currentDate.setText(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(now));
    }

    public void close(){
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        displayCurrentDate();
    }
}