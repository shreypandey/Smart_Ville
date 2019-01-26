package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;

public class Controller_Login {
    public JFXTextField email;
    public JFXPasswordField password;
    public JFXComboBox type;

    public void initialize(){
        type.getItems().addAll(Main.types);
    }
    public void onloginclicked(ActionEvent actionEvent) {

    }

    public void onsubjectclicked(ActionEvent actionEvent) {


    }
}
