package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MemberList extends AppCompatActivity {

    MyDatabaseHelper myDB = new MyDatabaseHelper(MemberList.this);
    RecyclerView recyclerViewForMemberList;
    CustomAdapterForMemberList customAdapterForMemberList;

    ArrayList<String> id, firstname, capital, purok, balance, amount;
    String getID;

    private CustomAdapterForMemberList.MemberListClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);

        NavigationView navigationView = findViewById(R.id.navigationView);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.member_list_menu_drawer_IV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuHome:
                        openDashboard();
                        return true;

                    case R.id.menuProfile:
                        openProfile();
                        return true;
                    case R.id.menuSignOut:
                        if(checkNetwork()){
                            SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("email", null);
                            editor.putString("password",null);
                            editor.putString("_id", null);
                            editor.putString("firstname",null);
                            editor.putString("middlename",null);
                            editor.putString("lastname",null);
                            editor.putString("phone_number",null);
                            editor.putString("company_id",null);
                            editor.putString("company_name",null);
                            editor.commit();
                            SignedOut();
                        }else{
                            Toast.makeText(MemberList.this, "Network not Available", Toast.LENGTH_SHORT).show();
                        }

                        return true;
                }

                return false;
            }
        });

        firstname = new ArrayList<>();
        capital = new ArrayList<>();
        balance = new ArrayList<>();
        id = new ArrayList<>();
        purok = new ArrayList<>();
        amount = new ArrayList<>();

        displayData();
        setOnClickListener();

        recyclerViewForMemberList = findViewById(R.id.recyclerViewForMemberList);
        customAdapterForMemberList = new CustomAdapterForMemberList(MemberList.this, id, firstname, purok, amount, listener);
        recyclerViewForMemberList.setAdapter(customAdapterForMemberList);
        recyclerViewForMemberList.setLayoutManager(new LinearLayoutManager(MemberList.this));

    }
    public void SignedOut(){
        //REMOVE ALL DB
        Cursor cursor = myDB.readAllDataFromDataTable();
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                myDB.deleteData(cursor.getString(0));
            }
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openProfile(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
    public void openDashboard(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    private void setOnClickListener() {
        listener = new CustomAdapterForMemberList.MemberListClickListener() {
            @Override
            public void onClick(View v, int position) {
                getID = id.get(position);
                openViewData();
            }
        };
    }

    void displayData(){
        Cursor cursor = myDB.readAllDataFromDataTable();

        if (cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){

                id.add(cursor.getString(0));
                firstname.add(cursor.getString(8));
                capital.add(cursor.getString(18));
                purok.add(cursor.getString(12));
                balance.add(cursor.getString(23));
                amount.add(cursor.getString(17));

            }
        }
    }

    public void openViewData(){
        Intent intent = new Intent(this, ViewData.class);

        intent.putExtra("id", getID);

        startActivity(intent);
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
}