package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button userBTNClick = findViewById(R.id.usersBTN);
        userBTNClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(checkNetwork()){
                   webViewShow();
               }else{
                   Toast.makeText(FirstActivity.this, "Network not Available", Toast.LENGTH_SHORT).show();
               }
            }
        });
        Button collectorBTNClick = findViewById(R.id.collectorBTN);
        collectorBTNClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectorShow();
            }
        });

    }
    public boolean checkNetwork(){
        try{
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if(manager!=null){
                networkInfo = manager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        }catch (NullPointerException e){
            return false;
        }
    }
    public void webViewShow(){
        Intent intent = new Intent(this, WebviewActivity.class);
        startActivity(intent);
    }
    public void collectorShow(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}