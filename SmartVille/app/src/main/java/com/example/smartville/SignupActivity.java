package com.example.smartville;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {

    EditText nametext,emailtext,passwordtext,phonetext;
    Button loginButton,Signupbutton;
    String email,fname,lname,phone,password,uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loginButton=findViewById(R.id.signup_loginButton);
        Signupbutton=findViewById(R.id.signup_signupButton);
        nametext=findViewById(R.id.signup_nameinput);
        emailtext=findViewById(R.id.signup_emailinput);
        passwordtext=findViewById(R.id.signup_passwordinput);
        phonetext=findViewById(R.id.signup_phoneinput);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        Signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailtext.getText().toString();
                fname=nametext.getText().toString();
                lname="";
                phone=phonetext.getText().toString();

                password=CreateHash.createHash(passwordtext.getText().toString());
                new SignupTask().execute();

            }
        });
    }
    class SignupTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            uid=UidGenerator.generateUID();
            String query="{\"query\":\"mutation add_insert_user{\\n  insert_User(objects:[{\\n   \\tId:\\\""+uid+"\\\"\\n    EmailId:\\\""+email+"\\\"\\n    FirstName : \\\""+fname+"\\\"\\n    LastName:\\\""+fname+"\\\"\\n    Password:\\\""+password+"\\\"\\n    ContactNo:\\\""+phone+"\\\"\\n  }\\n  ])\\n  {\\n    affected_rows\\n  }\\n}\",\"variables\":null,\"operationName\":\"add_insert_user\"}";
            try {


                URL url = new URL(MainActivity.hasuraurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(query);
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String output1="",output;
                while ((output = br.readLine()) != null) {
                    output1+=output;
                }
                Log.e("fatal",output1);
                SharedPreferences sharedpreferences = getSharedPreferences("Smart_Ville",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedpreferences.edit();
                editor.putString("UID",uid);
                editor.putString("email",email);
                editor.putString("fname",fname);
                editor.putString("lname",lname);
                editor.putString("contact",phone);
                editor.putString("status","true");
                editor.commit();
                Intent intent=new Intent(SignupActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();


                os.flush();
                os.close();

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
