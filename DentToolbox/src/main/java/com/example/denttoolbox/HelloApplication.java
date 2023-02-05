package com.example.denttoolbox;

import com.example.denttoolbox.datafetch.PositionSingleton;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class HelloApplication extends Application {

    PositionSingleton ps = PositionSingleton.getInstance();

    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Image image = new Image("D:\\Sprinkbut\\Zajaveczka\\DentToolbox\\src\\main\\java\\com\\example\\denttoolbox\\logodent.png");
        stage.getIcons().add(image);

        scene.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();

        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(0.6);

            ps.setX(event.getScreenX() - x);
            ps.setY(event.getScreenY() - y);

        });

        scene.setOnMouseReleased((MouseEvent) -> {
            stage.setOpacity(1);
        });

        stage.setTitle("Hello!");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}