package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import eu.bitm.NominatimReverseGeocoding.Address;
import eu.bitm.NominatimReverseGeocoding.NominatimReverseGeocodingJAPI;
import javafx.event.ActionEvent;
<<<<<<< HEAD
import javafx.fxml.FXML;
=======
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
>>>>>>> 5287e4623a2d296882297a8e19b97fb187976dfa

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Controller_Login
{
    public JFXTextField email;
    public JFXPasswordField password;
<<<<<<< HEAD
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
=======
    public JFXComboBox type;
    public void initialize()
    {
        type.getItems().addAll(Main.types);
    }
    public void onloginclicked(ActionEvent actionEvent) {
        String eid = email.getText();
        String pass = password.getText();
        String pass_act = "";
        String typ = (String) type.getSelectionModel().getSelectedItem();
        try {
            String url = "https://smart-ville.herokuapp.com/v1alpha1/graphql";
            URL object = new URL(url);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            String query = "{\"query\":\"\\nquery\\n{\\n  Authority(where:\\n  {\\n    EmailId:{_eq:\\\"uppolice@gov.in\\\"}})\\n  {\\n    Password\\n  }\\n}\\n\",\"variables\":null}";
            System.out.println(query);
            OutputStream os = con.getOutputStream();
            os.write(query.getBytes());
            os.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(output);
                JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");
                JSONArray jsonArray1 = (JSONArray) jsonObject1.get("Authority");
                for (int j = 0; j < jsonArray1.size(); j++) {
                    JSONObject jsonObject3 = (JSONObject) jsonArray1.get(j);
                    pass_act = (String) jsonObject3.get("Password");
                    System.out.println(pass_act);
                }
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pass_act == pass) {
            System.out.println("Login Verified by Authority");
            if (typ == "Police") {

            } else if (typ == "HealthCare") {

            } else if (typ == "FireSafety") {

            } else if (typ == "LostAndFound") {
>>>>>>> 5287e4623a2d296882297a8e19b97fb187976dfa

            } else if (typ == "Traffic") {

            } else if (typ == "Organising") {

            }
        }
    }
}
