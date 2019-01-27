package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Controller_login {
    @FXML
    JFXTextField email;
    @FXML
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
        Controller_organiser.getPotHoleData();

    }
    volatile String pass_act = "";
    public void onloginclicked(ActionEvent actionEvent) {
        String eid = email.getText();
        String pass = password.getText();

        String typ = (String) authoritytype.getSelectionModel().getSelectedItem();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                try {
                    String url = "https://smart-ville.herokuapp.com/v1alpha1/graphql";
                    URL object = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) object.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "application/json");
                    con.setRequestMethod("POST");
                    String query = "{\"query\":\"\\nquery\\n{\\n  Authority(where:\\n  {\\n    EmailId:{_eq:\\\""+eid+"\\\"}})\\n  {\\n    Password\\n  }\\n}\\n\",\"variables\":null}";
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
                    if(pass_act.equals(pass)) {
                        System.out.println("Login Verified by Authority");
                        if (typ.equals( "Police")) {
                            System.out.println("Congo");
                        } else if (typ .equals("HealthCare")) {

                        } else if (typ .equals("FireSafety")) {

                        } else if (typ .equals( "LostAndFound")) {


                        } else if (typ.equals( "Traffic")) {

                        } else if (typ.equals( "Organising")) {
                            System.out.println("CONGO");

                            Stage primaryStage = (Stage) email.getScene().getWindow();
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("organising.fxml"));
                            }catch(IOException e){
                                e.printStackTrace();
                            }
                            primaryStage.setScene(new Scene(root, 1081, 826));

                        }
                    }
                    con.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


//        }).start();
//
//
//    }

    public void onsubjectclicked(ActionEvent actionEvent) {
    }
}
