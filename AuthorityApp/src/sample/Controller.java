package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Controller
{
    public void getPotHoleData()
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
}
