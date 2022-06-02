package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyActivity extends AppCompatActivity {
    private static final String TAG = "CompanyActivity";
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        sp = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
        String _id = sp.getString("_id","");
        String company_id = sp.getString("company_id","");
        String company_name = sp.getString("company_name","");

        EditText company_idText = (EditText)findViewById(R.id.company_id);
        EditText _idText = (EditText)findViewById(R.id._id);
        EditText company_nameText = (EditText)findViewById(R.id.comapny_name);

        company_idText.setVisibility(View.GONE);
        _idText.setVisibility(View.GONE);

        company_idText.setText(company_id, TextView.BufferType.EDITABLE);
        _idText.setText(_id, TextView.BufferType.EDITABLE);
        company_nameText.setText(company_name, TextView.BufferType.EDITABLE);


        Button acceptBtn = findViewById(R.id.acceptBtn);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyAccept();
            }
        });

        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyCancel();
            }
        });

//        TextView scanTextViewBTN = findViewById(R.id.scanBtn);
//        scanTextViewBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openCompany();
//            }
//        });
        //end

    }
    public void companyAccept(){
        EditText company_idText = (EditText)findViewById(R.id.company_id);
        EditText _idText = (EditText)findViewById(R.id._id);
        String companyStr = company_idText.getText().toString();
        String _idStr = _idText.getText().toString();
        //API CALL
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Model> call = apiInterface.accept_companyInformation("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTA5NjI4MTR9.nR_lR_aesEN_LEM76uDOrLp5oFE83qwoyN3U4QeWM8w", _idStr, companyStr, true);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, response.body().getMessage(), duration);
                toast.show();
                openDashboard();
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });

    }
    public void companyCancel(){
        EditText company_idText = (EditText)findViewById(R.id.company_id);
        EditText _idText = (EditText)findViewById(R.id._id);
        String companyStr = company_idText.getText().toString();
        String _idStr = _idText.getText().toString();
        //API CALL
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Model> call = apiInterface.accept_companyInformation("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTA5NjI4MTR9.nR_lR_aesEN_LEM76uDOrLp5oFE83qwoyN3U4QeWM8w", _idStr, companyStr, false);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, response.body().getMessage(), duration);
                toast.show();
                SignedOut();
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });

    }
    public void SignedOut(){
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openCompany(){
        sp = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
        String _id = sp.getString("_id","");
        Log.e(TAG, "UserID "+_id);
        //API CALL
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Model> call = apiInterface.getCompanyInvitation("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTA5NjI4MTR9.nR_lR_aesEN_LEM76uDOrLp5oFE83qwoyN3U4QeWM8w", _id);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                //shared preferences
                Log.e(TAG, "OnSuccess"+response.body().getCompany_id());
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("company_id", response.body().getCompany_id());
                editor.putString("company_name", response.body().getCompany_name());
                editor.commit();
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "OnError");
            }
        });
        Intent intent = new Intent(this, CompanyActivity.class);
        startActivity(intent);
    }

    public void openDashboard(){
        Log.e(TAG, "DASHBOARDDDDD");
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}