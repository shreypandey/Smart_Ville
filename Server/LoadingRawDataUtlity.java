import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileHandling
{
    public static void main(String args[])
    {
        try
        {
            File file=new File("C:\\Users\\user\\IdeaProjects\\HasuraTest\\src\\test.csv");
            BufferedReader br= new BufferedReader(new FileReader(file));
            String output;
            String data[];
            while((output=br.readLine())!=null)
            {
                data=output.split(",");
                sendData(data);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void sendData(String data[])
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
            String query = "{\"query\":\"mutation add_insert_PotHolesRaw {\\n  insert_PotHolesRaw(objects: [{Latitude: \\\""+data[3]+"\\\",Longitude: \\\""+data[4]+"\\\",Acc_X:\\\""+data[0]+"\\\",Acc_Y:\\\""+data[1]+"\\\",Acc_Z:\\\""+data[2]+"\\\",UserId:\\\"descifrado\\\"}]) {\\n    affected_rows\\n  }\\n}\\n\",\"variables\":null,\"operationName\":\"add_insert_PotHolesRaw\"}";
            //String query="{\"query\":\"\\nquery\\n{\\n  hacktest\\n  {\\n    id\\n    name\\n  }\\n}\",\"variables\":null}";
            System.out.println(query);
            OutputStream os = con.getOutputStream();
            os.write(query.getBytes());
            os.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
            String output;
            while ((output = br.readLine()) != null)
            {
                System.out.println(output);
            }
            con.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
