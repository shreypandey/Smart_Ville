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
    }


    public static void main(String[] args) {
        launch(args);
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
            String id[]=new String[100000];
            String Latitude[]=new String[100000];
            String Longitude[]=new String[100000];
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
                    id[i]=(String) jsonObject3.get("Id");
                    Latitude[i]=(String)jsonObject3.get("Latitude");
                    Longitude[i]=(String) jsonObject3.get("Longitude");
                    i++;
                }
            }
            con.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
