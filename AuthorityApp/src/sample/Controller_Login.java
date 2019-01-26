package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import eu.bitm.NominatimReverseGeocoding.Address;
import eu.bitm.NominatimReverseGeocoding.NominatimReverseGeocodingJAPI;
import javafx.event.ActionEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Controller_Login
{
    public JFXTextField email;
    public JFXPasswordField password;
    public JFXComboBox type;
    public void initialize()
    {
        type.getItems().addAll(Main.types);
    }
<<<<<<< HEAD
    public void onloginclicked(ActionEvent actionEvent)
    {
        String eid=email.getText();
        String pass=password.getText();
        String pass_act="";
        String typ=(String)type.getSelectionModel().getSelectedItem();
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
            String query="{\"query\":\"\\nquery\\n{\\n  Authority(where:\\n  {\\n    EmailId:{_eq:\\\"uppolice@gov.in\\\"}})\\n  {\\n    Password\\n  }\\n}\\n\",\"variables\":null}";            System.out.println(query);
            OutputStream os = con.getOutputStream();
            os.write(query.getBytes());
            os.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
            String output;
            while ((output = br.readLine()) != null)
            {
                System.out.println(output);
                JSONParser jsonParser=new JSONParser();
                JSONObject jsonObject = (JSONObject)jsonParser.parse(output);
                JSONObject jsonObject1= (JSONObject) jsonObject.get("data");
                JSONArray jsonArray1=(JSONArray)jsonObject1.get("Authority");
                for(int j=0;j<jsonArray1.size();j++)
                {
                    JSONObject jsonObject3=(JSONObject) jsonArray1.get(j);
                    pass_act=(String) jsonObject3.get("Password");
                    System.out.println(pass_act);
                }
            }
            con.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if(pass_act==pass)
        {
            //Log in verified
        }
=======
    public void onloginclicked(ActionEvent actionEvent) {
        NominatimReverseGeocodingJAPI nominatim1 = new NominatimReverseGeocodingJAPI(); //create instance with default zoom level (18)

//        NominatimReverseGeocodingJAPI nominatim2 = new NominatimReverseGeocodingJAPI(18); //create instance with given zoom level

        Address address =  nominatim1.getAdress(19.10495377, 73.00523376); //returns Address object for the given position
        String addressString = ""+ address.getCity()+" "+address.getRoad() + " "+address.getSuburb();
        System.out.println(addressString);
>>>>>>> 52b941418536ffbe16af760f3551c0001558d105
    }
    public void onsubjectclicked(ActionEvent actionEvent)
    {

    }
}
