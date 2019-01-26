package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import eu.bitm.NominatimReverseGeocoding.Address;
import eu.bitm.NominatimReverseGeocoding.NominatimReverseGeocodingJAPI;
import javafx.event.ActionEvent;

public class Controller_Login {
    public JFXTextField email;
    public JFXPasswordField password;
    public JFXComboBox type;

    public void initialize(){
        type.getItems().addAll(Main.types);
    }
    public void onloginclicked(ActionEvent actionEvent) {
        NominatimReverseGeocodingJAPI nominatim1 = new NominatimReverseGeocodingJAPI(); //create instance with default zoom level (18)

//        NominatimReverseGeocodingJAPI nominatim2 = new NominatimReverseGeocodingJAPI(18); //create instance with given zoom level

        Address address =  nominatim1.getAdress(19.10495377, 73.00523376); //returns Address object for the given position
        String addressString = ""+ address.getCity()+" "+address.getRoad() + " "+address.getSuburb();
        System.out.println(addressString);
    }

    public void onsubjectclicked(ActionEvent actionEvent) {


    }
}
