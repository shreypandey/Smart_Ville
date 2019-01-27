package com.example.smartville;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

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

public class ShowPotholes extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    private String Latitude[]=new String[100000];
    private String Longitude[]=new String[100000];
    private boolean flag=true;
    private int num;
    private JSONObject jsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_potholes);
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
<<<<<<< HEAD:SmartVille/app/src/main/java/com/example/smartville/PotholeMap.java
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
=======
>>>>>>> f2d108739628c44e4d1063168f7debf6118195b7:SmartVille2/app/src/main/java/com/example/smartville/ShowPotholes.java
        new loadPotholes().execute("hello");
        while(flag){

        }
        LatLng mark[]=new LatLng[num];
<<<<<<< HEAD:SmartVille/app/src/main/java/com/example/smartville/PotholeMap.java
        for (int j = 0; j <+ num; j++) {
=======
        int j;
        for (j = 0; j <+ num; j++) {
>>>>>>> f2d108739628c44e4d1063168f7debf6118195b7:SmartVille2/app/src/main/java/com/example/smartville/ShowPotholes.java
            LatLng temp=new LatLng(Double.parseDouble(Latitude[j]), Double.parseDouble(Longitude[j]));
            mark[j]=temp;
            mMap.addMarker(new MarkerOptions().position(mark[j]).title("Marker in Sydney"));
<<<<<<< HEAD:SmartVille/app/src/main/java/com/example/smartville/PotholeMap.java
            Log.e("MArked at",mark[j].latitude+""+mark[j].longitude);
=======

>>>>>>> f2d108739628c44e4d1063168f7debf6118195b7:SmartVille2/app/src/main/java/com/example/smartville/ShowPotholes.java
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mark[j]));
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
                JSONObject jsonObj = new JSONObject(output1);
                JSONObject jsonObj2=(JSONObject) jsonObj.get("data");
                JSONArray jarr = (JSONArray) jsonObj2.get("PotHolesMain");
                for(int j=0;j<jarr.length();j++)
                {
                    JSONObject jsonObject3=(JSONObject) jarr.get(j);
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
        }
    }
}

