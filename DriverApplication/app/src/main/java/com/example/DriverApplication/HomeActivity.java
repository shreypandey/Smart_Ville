package com.example.DriverApplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class HomeActivity extends AppCompatActivity implements SensorEventListener{
    double lat;
    double lon;
    private SensorManager mSensorManager;
    private Sensor mAcc;
    private FusedLocationProviderClient mFusedLocationClient;

    private boolean isNetworkConnected() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAcc, SensorManager.SENSOR_DELAY_NORMAL);
        new SendData().execute();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String filename = "sensorData";
        getLocation();
        SharedPreferences sharedpreferences = getSharedPreferences("DriverApplication", Context.MODE_PRIVATE);
        String uid=sharedpreferences.getString("UID","Unknown");
        String query = "{\"query\":\"mutation add_insert_PotHolesRaw {\\n  insert_PotHolesRaw(objects: [{Latitude: \\\""+lat+"\\\",Longitude: \\\""+lon+"\\\",Acc_X:\\\""+event.values[0]+"\\\",Acc_Y:\\\""+event.values[1]+"\\\",Acc_Z:\\\""+event.values[2]+"\\\",UserId:\\\""+uid+"\\\"}]) {\\n    affected_rows\\n  }\\n}\\n\",\"variables\":null,\"operationName\":\"add_insert_PotHolesRaw\"}";

        //FileOutputStream outputStream;


        try {
            File myfile=new File(this.getFilesDir(),"sensorData");
            if(!myfile.exists()){
                myfile.createNewFile();
            }
            FileWriter fw ;
            fw = new FileWriter(myfile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            /*outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(query.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.close();*/
            out.println(query);
            out.close();
        } catch (Exception e) {
            Log.e("fatal",e+"");
        }
    }

    private void getLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return ;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(HomeActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            lat=location.getLatitude();
                            lon=location.getLongitude();// Logic to handle location object
                        }
                    }
                });

        return ;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class SendData extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            while(true){
                if (isNetworkConnected()){
                    try {
                        String query;
                        FileInputStream inputStream;
                        BufferedReader br1;
                        try {
                            inputStream = openFileInput("sensorData");
                            br1 = new BufferedReader(new InputStreamReader(inputStream));
                            while ((query = br1.readLine()) != null){
                                System.out.println(query);
                            URL url = new URL(MainActivity.hasuraurl);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                            os.writeBytes(query);
                            BufferedReader br = new BufferedReader(new InputStreamReader(
                                    (conn.getInputStream())));
                            String output1 = "", output;
                            while ((output = br.readLine()) != null) {
                                output1 += output;
                            }
                            Log.e("fatal", output1);


                            os.flush();
                            os.close();

                            conn.disconnect();}
                            FileOutputStream outputStream = openFileOutput("sensorData", Context.MODE_PRIVATE);
                            outputStream.write("".getBytes());
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                 catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
