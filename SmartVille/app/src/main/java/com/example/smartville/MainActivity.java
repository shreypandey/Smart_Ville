package com.example.smartville;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    static String hasuraurl="https://smart-ville.herokuapp.com/v1alpha1/graphql";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedpreferences = getSharedPreferences("Smart_Ville", Context.MODE_PRIVATE);
                String string=sharedpreferences.getString("status","false");
                if (string.equals("false")){
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },1000);
    }
}
