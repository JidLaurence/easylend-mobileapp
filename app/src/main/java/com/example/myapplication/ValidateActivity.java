package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateActivity extends AppCompatActivity {
    Button verifyBtn;
    SharedPreferences sp;

    private static final String TAG = "ValidateActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);

        EditText user_idHide = (EditText)findViewById(R.id.user_id_code);
        user_idHide.setVisibility(View.GONE);

        //SharedPreferences
        SharedPreferences spset = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
        String _id = spset.getString("_id","");
        user_idHide.setText(_id, TextView.BufferType.EDITABLE);

        verifyBtn = findViewById(R.id.signup_signup_button3);
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCode();
            }
        });

    }
    public void checkCode(){
        EditText code = (EditText)findViewById(R.id.code);
        EditText user_id = (EditText)findViewById(R.id.user_id_code);

        String codeStr = code.getText().toString();
        String userStr = user_id.getText().toString();
        Log.e(TAG, "codeStr"+codeStr);
        Log.e(TAG, "codeStr"+userStr);
        //API CALL
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Model> call = apiInterface.updateCodeInformation("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTA5NjI4MTR9.nR_lR_aesEN_LEM76uDOrLp5oFE83qwoyN3U4QeWM8w", userStr, codeStr);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Log.e(TAG, "OnScccuess"+ response.body().getMessage());
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, response.body().getMessage(), duration);
                toast.show();
                OpenCompany();
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "OnError");
            }
        });
    }
    public void OpenCompany(){
        Intent intent = new Intent(this, CompanyActivity.class);
        startActivity(intent);
    }
}