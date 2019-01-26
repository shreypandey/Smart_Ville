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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NearbyFacilities extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //list of LatLng and string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_nearby_facilities);
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
        new loadFacilities().execute("hello");
        new addMarkers().execute();

    }

    class loadFacilities extends AsyncTask<String,String,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            String query;// = "{\"query\":\"query {\\n  User(\\n    where: {EmailId: {_eq: \\\""+email+"\\\"}}\\n  ) {\\n    EmailId\\n    Password\\n    FirstName\\n    LastName\\n    Id\\n    ContactNo\\n  }\\n}\",\"variables\":null}";
            try {
                URL url = new URL(MainActivity.hasuraurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(query);
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String output1 = "", output;
                while ((output = br.readLine()) != null) {
                    output1 += output;
                }
                Log.e("fatal", output1);
                JSONObject jsonObj = new JSONObject(output1);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            Void a=null;
            return a;
        }

        @Override
        protected void onPostExecute(Void a) {
            super.onPostExecute(a);


        }
    }


    class addMarkers extends AsyncTask<String,String,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            Void a=null;
            return a;
        }

        @Override
        protected void onPostExecute(Void a) {
            super.onPostExecute(a);


        }
    }
}

