package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class Controller_organiser {

    public JFXTextArea txtarea;


    @FXML
    JFXButton back;

//    String id[]=new String[100000];
    static String Latitude[]=new String[100000];
    static String Longitude[]=new String[100000];
    static  int size;

    public void initialize(){
//        getPotHoleData();
        String s= "Latitude  Longitude  Location\n";
    }

    public static void getPotHoleData()
    {
        try
        {
            String url="https://smart-ville.herokuapp.com/v1alpha1/graphql";
            URL object=new URL(url);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            String query="{\"query\":\"\\nquery\\n{\\n  PotHolesMain\\n  {\\n    Id\\n    Latitude\\n    Longitude\\n}\\n}\",\"variables\":null}";
            System.out.println(query);
            OutputStream os = con.getOutputStream();
            os.write(query.getBytes());
            os.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
            String output;

            int i=0;
            while ((output = br.readLine()) != null)
            {
                System.out.println(output);
                JSONParser jsonParser=new JSONParser();
                JSONObject jsonObject = (JSONObject)jsonParser.parse(output);
                JSONObject jsonObject1= (JSONObject) jsonObject.get("data");
                JSONArray jsonArray1=(JSONArray)jsonObject1.get("PotHolesMain");
                for(int j=0;j<jsonArray1.size();j++)
                {
                    JSONObject jsonObject3=(JSONObject) jsonArray1.get(j);
//                    id[i]=(String) jsonObject3.get("Id");
                    Latitude[i]=(String)jsonObject3.get("Latitude");
                    Longitude[i]=(String) jsonObject3.get("Longitude");
                    i++;
                }
            }
            size = i;
            con.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void onbackclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("login.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }

    public void ondataclicked(ActionEvent actionEvent) {
        String s = "Lat    Long              Loc\n";
        for(int i=0;i<12;i++){
            s+=(""+Latitude[i]+"  "+Longitude[i] + "  "+Main.getAddress(Double.parseDouble(Latitude[i]),Double.parseDouble(Longitude[i]))+"\n");
        }
        txtarea.setText(s);
    }
}
