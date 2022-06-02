package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {

    MyDatabaseHelper myDB = new MyDatabaseHelper(Signup.this);
    SharedPreferences sp;
    EditText login_email_input_ET, login_password_input_ET;
    private static final String TAG = "Signup";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        String signup_register_here_string = "You can Sign in Here";
        TextView signup_login_here_TV = findViewById(R.id.signup_login_here_TV);

        SpannableString signup_register_here_spannable_string = new SpannableString(signup_register_here_string);
        ClickableSpan signup_register_here_clickable_span = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                OpenLogin();
            }
        };

        signup_register_here_spannable_string.setSpan(signup_register_here_clickable_span, 8, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signup_register_here_spannable_string.setSpan(new ForegroundColorSpan(Color.BLUE), 8, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signup_login_here_TV.setText(signup_register_here_spannable_string);
        signup_login_here_TV.setMovementMethod(LinkMovementMethod.getInstance());



        Button signup_signup_button = findViewById(R.id.signup_signup_button);
        signup_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkNetwork()){
                    login_email_input_ET = (EditText)findViewById(R.id.signup_email_input_ET);
                    login_password_input_ET =  (EditText)findViewById(R.id.signup_password_input_ET);
                    String email = login_email_input_ET.getText().toString();
                    String password = login_password_input_ET.getText().toString();
                    //SharedPreferences
                    sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                    //API CALL
                    ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                    Call<Model> call = apiInterface.signupUserInformation("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTA5NjI4MTR9.nR_lR_aesEN_LEM76uDOrLp5oFE83qwoyN3U4QeWM8w", email, password);
                    call.enqueue(new Callback<Model>() {
                        @Override
                        public void onResponse(Call<Model> call, Response<Model> response) {
                            //TOAST
                            Context context = getApplicationContext();
                            CharSequence text = response.body().getMessage();
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            if(response.body().getStatus()==true){
                                OpenLogin();
                            }
                        }

                        @Override
                        public void onFailure(Call<Model> call, Throwable t) {
                            Log.e(TAG,"onFailure: " + t.getMessage());
                        }
                    });
                }else{
                    Toast.makeText(Signup.this, "Network not Available", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    public void OpenLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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