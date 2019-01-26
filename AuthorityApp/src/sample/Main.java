package sample;
import eu.bitm.NominatimReverseGeocoding.Address;
import eu.bitm.NominatimReverseGeocoding.NominatimReverseGeocodingJAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Authority-App");
        primaryStage.setScene(new Scene(root, 1081, 826));
//        primaryStage.setMaximized(true);
        primaryStage.show();

        types.add(("LostAndFound"));
        types.add("Police");
        types.add("HealthCare");
        types.add("FireSafety");
        types.add("Traffic");
        types.add("Organising");


    }

    static
    {
        System.setProperty("java.net.useSystemProxies", "true");
    }

    static List<String> types = new ArrayList<>();


    public static void main(String[] args){
        Application.launch(args);
    }
}