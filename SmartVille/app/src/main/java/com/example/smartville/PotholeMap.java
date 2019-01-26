package com.example.smartville;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeFormatException;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;

public class PotholeMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String Latitude[]=new String[100000];
    private String Longitude[]=new String[100000];
    private boolean flag=true;
    private int num;
    private JSONObject jsonObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pothole_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        new loadPotholes().execute("hello");
        Log.e("Chalo","MC");
        while(flag){

        }
        LatLng mark[]=new LatLng[num];
        for (int j = 0; j <+ num; j++) {
            LatLng temp=new LatLng(Double.parseDouble(Latitude[j]), Double.parseDouble(Longitude[j]));
            mark[j]=temp;

            mMap.addMarker(new MarkerOptions().position(mark[j]).title("Marker in Sydney"));
            Log.e("MArked at",mark[j].latitude+""+mark[j].longitude);
        }
    }

    class loadPotholes extends AsyncTask<String,String,Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String query="{\"query\":\"query {\\n  PotHolesMain{\\n    Latitude\\n    Longitude\\n  }\\n}\\n\",\"variables\":null}";// = "{\"query\":\"query {\\n  User(\\n    where: {EmailId: {_eq: \\\""+email+"\\\"}}\\n  ) {\\n    EmailId\\n    Password\\n    FirstName\\n    LastName\\n    Id\\n    ContactNo\\n  }\\n}\",\"variables\":null}";
            try {
                URL url = new URL(MainActivity.hasuraurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                while(conn==null){

                }
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(query);
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String output1 = "", output;
                while ((output = br.readLine()) != null) {
                    output1 += output;
                }
                Log.e("fatal", output1);
                //JSONParser jsonpar=new JSONParser();
                JSONObject jsonObj = new JSONObject(output1);
                JSONObject jsonObj2=(JSONObject) jsonObj.get("data");
                JSONArray jarr = (JSONArray) jsonObj2.get("PotHolesMain");
                for(int j=0;j<jarr.length();j++)
                {
                    JSONObject jsonObject3=(JSONObject) jarr.get(j);
                    //id[i]=(String) jsonObject3.get("Id");
                    Latitude[j]=(String)jsonObject3.get("Latitude");
                    Longitude[j]=(String) jsonObject3.get("Longitude");
                    Log.e(Latitude[j],Longitude[j]);
                    num=j;
                }
                Log.e("data","fetched2");

            }
            catch (Exception e){
                e.printStackTrace();
                Log.e("fatal",""+e.getMessage());
            }
            Log.e("data","fetched3");
            flag=false;
            Void a=null;
            return a;
        }

        @Override
        protected void onPostExecute(Void a) {
            Log.e("data","fetched");
            flag=false;
            super.onPostExecute(a);
            //flag=false;

        }
    }
}

