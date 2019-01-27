package sample;

import eu.bitm.NominatimReverseGeocoding.Address;
import eu.bitm.NominatimReverseGeocoding.NominatimReverseGeocodingJAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    static List<String> types = new ArrayList<>();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Authority_App");
        primaryStage.setScene(new Scene(root, 1081, 826));
        primaryStage.show();
//        Controller_organiser.getPotHoleData();
    }


    public static void main(String[] args) {
        launch(args);
    }




    public static String getAddress(double lat,double lon)
    {
        NominatimReverseGeocodingJAPI nominatim1 = new NominatimReverseGeocodingJAPI(); //create instance with default zoom level (18)
        //NominatimReverseGeocodingJAPI nominatim2 = new NominatimReverseGeocodingJAPI(18); //create instance with given zoom level
        Address address =  nominatim1.getAdress(lat, lon); //returns Address object for the given position
        String addressString = ""+ address.getCity()+" "+address.getRoad() + " "+address.getSuburb();
        return  addressString;
    }



}
