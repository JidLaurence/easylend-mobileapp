package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class Profile extends AppCompatActivity {
    MyDatabaseHelper myDB = new MyDatabaseHelper(Profile.this);
    Button update_Profile_Btn;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        //SharedPreferences
        SharedPreferences spset = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);

        String _id = spset.getString("_id","");
        String email = spset.getString("email","");
        String password = spset.getString("password","");
        String firstname = spset.getString("firstname","");
        String middlename = spset.getString("middlename","");
        String lastname = spset.getString("lastname","");
        String phone = spset.getString("phone_number","");

        EditText editTextUserID = (EditText)findViewById(R.id.user_id);
        editTextUserID.setVisibility(View.GONE);

        EditText editTextEmail = (EditText)findViewById(R.id.editTextTextPersonName12);
        EditText editTextPassword = (EditText)findViewById(R.id.editTextTextPersonName15);

        EditText editTextfirstname = (EditText)findViewById(R.id.editTextTextPersonName9);
        EditText editTextmiddlename = (EditText)findViewById(R.id.editTextTextPersonName10);
        EditText editTextlastname = (EditText)findViewById(R.id.editTextTextPersonName11);
        EditText editTextPhone = (EditText)findViewById(R.id.editTextTextPersonName13);

        editTextEmail.setText(email, TextView.BufferType.EDITABLE);
        editTextUserID.setText(_id, TextView.BufferType.EDITABLE);
        editTextPassword.setText(password, TextView.BufferType.EDITABLE);

        editTextfirstname.setText(firstname, TextView.BufferType.EDITABLE);
        editTextmiddlename.setText(middlename, TextView.BufferType.EDITABLE);
        editTextlastname.setText(lastname, TextView.BufferType.EDITABLE);
        editTextPhone.setText(phone, TextView.BufferType.EDITABLE);
        //UPDATE PROFILE
        update_Profile_Btn = findViewById(R.id.updateProfileBtn);
        update_Profile_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SharedPreferences
                SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);

                EditText editTextUserID = (EditText)findViewById(R.id.user_id);
                EditText editTextPassword = (EditText)findViewById(R.id.editTextTextPersonName15);
                EditText editTextfirstname = (EditText)findViewById(R.id.editTextTextPersonName9);
                EditText editTextmiddlename = (EditText)findViewById(R.id.editTextTextPersonName10);
                EditText editTextlastname = (EditText)findViewById(R.id.editTextTextPersonName11);
                EditText editTextPhone = (EditText)findViewById(R.id.editTextTextPersonName13);

                String _id = editTextUserID.getText().toString();
                String password = editTextPassword.getText().toString();
                String fname = editTextfirstname.getText().toString();
                String mname = editTextmiddlename.getText().toString();
                String lname = editTextlastname.getText().toString();
                String phone = editTextPhone.getText().toString();

                //API CALL
                ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                Call<Model> call = apiInterface.updateUserInformation("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTA5NjI4MTR9.nR_lR_aesEN_LEM76uDOrLp5oFE83qwoyN3U4QeWM8w", _id, fname,mname,lname,phone,password);
                call.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        //TOAST
                        Context context = getApplicationContext();
                        CharSequence text = response.body().getMessage();
                        int duration = Toast.LENGTH_SHORT;

                        //TOAST
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        if(response.body().getStatus()==true){

                            EditText editTextPassword = (EditText)findViewById(R.id.editTextTextPersonName15);
                            EditText editTextfirstname = (EditText)findViewById(R.id.editTextTextPersonName9);
                            EditText editTextmiddlename = (EditText)findViewById(R.id.editTextTextPersonName10);
                            EditText editTextlastname = (EditText)findViewById(R.id.editTextTextPersonName11);
                            EditText editTextPhone = (EditText)findViewById(R.id.editTextTextPersonName13);
                            String password = editTextPassword.getText().toString();
                            String fname = editTextfirstname.getText().toString();
                            String mname = editTextmiddlename.getText().toString();
                            String lname = editTextlastname.getText().toString();
                            String phone = editTextPhone.getText().toString();

                            Log.e(TAG,"onSuccess: " + fname);
                            //shared preferences
                            SharedPreferences spUpdated = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = spUpdated.edit();
                            editor2.putString("password", password);
                            editor2.putString("firstname",fname);
                            editor2.putString("middlename",mname);
                            editor2.putString("lastname",lname);
                            editor2.putString("phone_number", phone);
                            editor2.commit();
                            openProfile();
                        }
                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Log.e(TAG,"onFailure: " + t.getMessage());
                    }
                });
            }

        });
        //END

        NavigationView navigationView = findViewById(R.id.navigationView);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.profile_menu_drawer_IV).setOnClickListener(new View.OnClickListener() {
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
                            SignedOut();
                        }else{
                            Toast.makeText(Profile.this, "Network not Available", Toast.LENGTH_SHORT).show();
                        }

                        return true;
                }

                return false;
            }
        });



    }

    public void SignedOut(){
        //REMOVE USER
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