package com.company;

import py4j.GatewayServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class Main {

    public String  addition(String request) {
        try{
            String url="https://smart-ville.herokuapp.com/v1alpha1/graphql";
            URL object=new URL(url);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            String query = request;
//            System.out.println(query);

            OutputStream os = con.getOutputStream();
            os.write(query.getBytes());
            os.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (con.getInputStream())));
            String output1="",output;
            while ((output = br.readLine()) != null) {
                output1+=output;
            }

            con.disconnect();
            return output1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        Main app = new Main();
        // app is now the gateway.entry_point
        GatewayServer server = new GatewayServer(app);
        server.start();
    }
}