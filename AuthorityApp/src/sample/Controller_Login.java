package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import eu.bitm.NominatimReverseGeocoding.Address;
import eu.bitm.NominatimReverseGeocoding.NominatimReverseGeocodingJAPI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller_Login {
    public JFXTextField email;
    public JFXPasswordField password;
    @FXML
    JFXComboBox authoritytype;

    public void initialize(){
        Main.types.add(("LostAndFound"));
        Main.types.add("Police");
        Main.types.add("HealthCare");
        Main.types.add("FireSafety");
        Main.types.add("Traffic");
        Main.types.add("Organising");
        authoritytype.getItems().addAll(Main.types);
    }
    public void onloginclicked(ActionEvent actionEvent) {

    }

    public void onsubjectclicked(ActionEvent actionEvent) {


    }
}
