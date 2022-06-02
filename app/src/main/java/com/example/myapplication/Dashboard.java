package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {
    MyDatabaseHelper myDB = new MyDatabaseHelper(Dashboard.this);
    String from_login_email;
    String rowIndex;
    ArrayList<String> id, firstname, capital, purok, balance, amount;
    private static final String TAG = "Dashboard";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

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
                        return true;
                }

                return false;
            }
        });

        ImageView dashboard_member_list_button = findViewById(R.id.dashboard_member_list_button);
        dashboard_member_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMemberList();
            }
        });

        ImageView dashboard_create_member_IV = findViewById(R.id.dashboard_create_member_IV);
        dashboard_create_member_IV.setVisibility(View.GONE);
        dashboard_create_member_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TOAST
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, "Coming soon..", duration);
                toast.show();
               // OpenCreateMember();
            }
        });
        ImageView uploadDataBtn = findViewById(R.id.uploadListImg);
        uploadDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkNetwork()){
                    uploadDataOnline();
                }else{
                    Toast.makeText(Dashboard.this, "Network not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageView downloadDataBtn = findViewById(R.id.imageView8);
        downloadDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkNetwork()){
                    downloadDataOnline();
                }else{
                    Toast.makeText(Dashboard.this, "Network not Available", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void downloadDataOnline(){
        //SharedPreferences
        SharedPreferences spset = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
        String email = spset.getString("email","");
        String password = spset.getString("password","");
        //SharedPreferences

        //API CALL
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Model> call = apiInterface.getUserInformation("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTA5NjI4MTR9.nR_lR_aesEN_LEM76uDOrLp5oFE83qwoyN3U4QeWM8w", email, password);
       call.enqueue(new Callback<Model>() {
           @Override
           public void onResponse(Call<Model> call, Response<Model> response) {
               ArrayList<Model.data> data = response.body().getData();
               for(Model.data data1 : data) {
                   Cursor cursor = myDB.readAllDataFromDataTable();
                   Boolean status = false;
                   while (cursor.moveToNext()){
                       if(data1.get_id().equals(cursor.getString(1)) &&
                               data1.getMonth()==cursor.getInt(27) &&
                               data1.getDay() == cursor.getInt(28) &&
                                data1.getYear() == cursor.getInt(29)
                       ){
                           status=true;
                       }
                   }
                   //DONWLOAD API DATA IF LOCAL SORAGE IS NOT EQUAL TO API DATA
                   if(status==false){
                       //save local storage
                       myDB.addData(data1.getCompany_id(), data1.getBranch_id(), data1.getStaff_id(), response.body().get_id(),
                               data1.get_id(), data1.getFirstname(), data1.getLastname(), data1.getMiddlename(), data1.getPhone_number(),
                               data1.getAddress(), data1.getBarangay(), data1.getCity(), data1.getProvince(), data1.getRegion(), data1.getBalance(),
                               data1.getCollect(), data1.getCapital(), data1.getMonth(), data1.getDay(), data1.getYear(), data1.getAmount()
                       );
                   }
               }
               //TOAST
               Context context = getApplicationContext();
               int duration = Toast.LENGTH_SHORT;
               Toast toast = Toast.makeText(context, "Successfully downloaded", duration);
               toast.show();
           }

           @Override
           public void onFailure(Call<Model> call, Throwable t) {

           }
       });

    }
    public void uploadDataOnline(){
        Cursor cursor = myDB.readAllDataFromDataTable();
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                String customer_db_id = cursor.getString(1);
                String company_id = cursor.getString(2);
                String branch_id =  cursor.getString(3);
                String staff_id = cursor.getString(4);
                String collector_id = cursor.getString(5);
                Integer amount = cursor.getInt(17);
                Integer month = cursor.getInt(27);
                Integer day = cursor.getInt(28);
                Integer year = cursor.getInt(29);

                if(amount>0){
                    Log.e(TAG, "AMOUNT "+ amount);
                    //SEND DATA TO API
                    Call<Model> call = apiInterface.saveCollected(
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTA5NjI4MTR9.nR_lR_aesEN_LEM76uDOrLp5oFE83qwoyN3U4QeWM8w",
                            customer_db_id,
                            company_id,
                            branch_id,
                            staff_id,
                            collector_id,
                            amount,
                            month,
                            day,
                            year
                    );
                    call.enqueue(new Callback<Model>() {
                        @Override
                        public void onResponse(Call<Model> call, Response<Model> response) {
                            Log.e(TAG, "Uploaded..");
                        }

                        @Override
                        public void onFailure(Call<Model> call, Throwable t) {
                            Log.e(TAG, "OnError");
                        }
                    });
                    //END CALL

                    //REMOVE AFTER ALL DATA DELETED;
                    myDB.deleteData(cursor.getString(0));
                }


            }
            //TOAST
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "Successfully uploaded", duration);
            toast.show();
        }
    }
    public void SignedOut(){
        if(checkNetwork()){
            //REMOVE ALL DB
            Cursor cursor = myDB.readAllDataFromDataTable();
            if (cursor.getCount() != 0){
                while (cursor.moveToNext()){
                    myDB.deleteData(cursor.getString(0));
                }
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(Dashboard.this, "Network not Available", Toast.LENGTH_SHORT).show();
        }
    }

    public void OpenMemberList(){
        Intent intent = new Intent(this, MemberList.class);
        startActivity(intent);
    }

    public void OpenCreateMember(){
        Intent intent = new Intent(this, CreateNewMember.class);
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