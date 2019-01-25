package com.example.smartville;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;
public class LoginActivity extends AppCompatActivity {

    EditText emailtext,passwordtext;
    Button loginButton,Signupbutton;
    String passwordget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailtext=findViewById(R.id.login_emailinput);
        passwordtext=findViewById(R.id.login_passwordinput);
        loginButton=findViewById(R.id.login_loginButton);
        Signupbutton=findViewById(R.id.login_newaccountButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailtext.getText().toString();
                String password=CreateHash.createHash(passwordtext.getText().toString());
                new LoginTask().execute(email);

                if (passwordget==null){
                    emailtext.setError("Email Does not exist");
                }
                else {
                    Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    class LoginTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String passwords1=null;
            try {
                String email=strings[0];
                String query = "{\"query\":\"query {\\n  User(\\n    where: {EmailId: {_eq: \\\""+email+"\\\"}}\\n  ) {\\n    EmailId\\n    Password\\n  }\\n}\",\"variables\":null}";

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
                JSONObject jsonObj = new JSONObject(output1);
                jsonObj=jsonObj.getJSONObject("data");
                JSONArray data = jsonObj.getJSONArray("User");
                if (data==null){
                    passwordget=null;
                    return null;
                }
                String emails=data.getString(0);
                jsonObj=new JSONObject(emails);
                passwords1=jsonObj.getString("Password");

                os.flush();
                os.close();

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            passwordget=passwords1;
            return passwords1;
        }
    }
}
