package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Tag;

public class MainActivity extends AppCompatActivity {

    EditText login_email_input_ET, login_password_input_ET;
    Button login_login_button;

    MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
    String from_login_email;
    //JED
    private static final String TAG = "MainActivity";
    SharedPreferences sp;
    //SAVE ARRAYYLIST
    ArrayList<MemberItem> memberitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SharedPreferences
        SharedPreferences sp = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
        String email = sp.getString("email","");

        //check if email
        if(!email.isEmpty() && !email.equals("null")){
            openDashboard();
        }



        String login_register_here_string = "You can Register Here";
        TextView login_register_here_TV = findViewById(R.id.login_register_here_TV);

        SpannableString login_register_here_spannable_string = new SpannableString(login_register_here_string);

       ClickableSpan login_register_here_clickable_span = new ClickableSpan() {
           @Override
           public void onClick(@NonNull View view) {
               OpenSignup();
           }
       };
        login_register_here_spannable_string.setSpan(login_register_here_clickable_span, 8, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        login_register_here_spannable_string.setSpan(new ForegroundColorSpan(Color.BLUE), 8, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        login_register_here_TV.setText(login_register_here_spannable_string);
        login_register_here_TV.setMovementMethod(LinkMovementMethod.getInstance());


        login_login_button = findViewById(R.id.login_login_button);
        login_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkNetwork()){
                    login_email_input_ET = findViewById(R.id.login_email_input_ET);
                    login_password_input_ET = findViewById(R.id.login_password_input_ET);

                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;

                    if(TextUtils.isEmpty(login_email_input_ET.getText().toString()) || TextUtils.isEmpty(login_password_input_ET.getText().toString())){
                        Toast toast = Toast.makeText(context, "Please fill up the form", duration);
                        toast.show();
                    }else{
                        Toast toast = Toast.makeText(context, "Loading..", duration);
                        toast.show();
                        loginBTNClick();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Network not Available", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    //JED
    private void loginBTNClick() {

        login_email_input_ET = (EditText)findViewById(R.id.login_email_input_ET);
        login_password_input_ET = findViewById(R.id.login_password_input_ET);
        String email = login_email_input_ET.getText().toString();
        String password = login_password_input_ET.getText().toString();

        //SharedPreferences
        sp = getSharedPreferences("user", Context.MODE_PRIVATE);

        //API CALL
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Model> call = apiInterface.getUserInformation("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NTA5NjI4MTR9.nR_lR_aesEN_LEM76uDOrLp5oFE83qwoyN3U4QeWM8w", email, password);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                //TOAST
                Context context = getApplicationContext();
                CharSequence text = response.body().getMessage();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Log.e(TAG, "COMPANY NAME"+ response.body().getCompany_name());

                //shared preferences
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("_id", response.body().get_id());
                editor.putString("email", response.body().getEmail());
                editor.putString("password",response.body().getPassword());
                editor.putString("firstname",response.body().getFirstname());
                editor.putString("middlename",response.body().getMiddlename());
                editor.putString("lastname",response.body().getLastname());
                editor.putString("phone_number", response.body().getPhone_number());

                editor.putString("company_id", response.body().getCompany_id());
                editor.putString("company_name", response.body().getCompany_name());
                editor.commit();

                if(response.body().getStatus()==false) {

                }else if(response.body().getVerified()==false){
                    Log.e(TAG,"onResponse: status gerVerified"+ response.body().getVerified());
                    OpenVerify();
                }else if(response.body().getCompanyStatus()==false){
                    Log.e(TAG,"onResponse: status getCompanyStatus"+ response.body().getCompanyStatus());
                    OpenCompany();
                }else if(response.body().getUpdated()==false){
                    Log.e(TAG,"onResponse: status getUpdated"+ response.body().getUpdated());
                    OpenProfile();
                }else if(response.body().getStatus()==true){
                    //ARRAY OF DATA
                    ArrayList<Model.data> data = response.body().getData();
                    StringBuilder stringBuilder = new StringBuilder();

                    for(Model.data data1 : data){
                        Log.e(TAG, "DATA "+
                                        data1.getCompany_id()+"-"+ data1.getBranch_id()+"-"+ data1.getStaff_id()+"-"+ response.body().get_id()+"-"+
                                data1.get_id()+"-"+ data1.getFirstname()+"-"+ data1.getLastname()+"-"+ data1.getMiddlename()+"-"+ data1.getPhone_number()+"-"+
                                data1.getAddress()+"-"+ data1.getBarangay()+"-"+ data1.getCity()+"-"+ data1.getProvince()+"-"+ data1.getRegion()+"-"+ data1.getBalance()+"-"+
                                data1.getCollect()+"-"+ data1.getCapital()+"-"+ data1.getMonth()+"-"+ data1.getDay()+"-"+ data1.getYear()+"-"+data1.getAmount()
                        );

                        //save local storage
                        myDB.addData(data1.getCompany_id(), data1.getBranch_id(), data1.getStaff_id(), response.body().get_id(),
                               data1.get_id(), data1.getFirstname(), data1.getLastname(), data1.getMiddlename(), data1.getPhone_number(),
                                data1.getAddress(), data1.getBarangay(), data1.getCity(), data1.getProvince(), data1.getRegion(), data1.getBalance(),
                                data1.getCollect(), data1.getCapital(), data1.getMonth(), data1.getDay(), data1.getYear(),data1.getAmount()
                        );

                    }
                    openDashboard();
                }

            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG,"onFailure: " + t.getMessage());
            }
        });
    }
    private void loadData(){
        //SharedPreferences for data
        SharedPreferences spdata = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = spdata.edit();
        Gson gson = new Gson();
        String json = spdata.getString("list_data", null);
        Type type = new TypeToken<ArrayList<MemberItem>>(){

        }.getType();
        memberitems=gson.fromJson(json, type);
        if(memberitems==null){
            memberitems=new ArrayList<>();
        }
    }
    public void OpenSignup(){
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
        finish();
    }

    public void openDashboard(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
    //JED
    public void OpenProfile(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
    }

    public void OpenMemberList(){
        Intent intent = new Intent(this, MemberList.class);
        startActivity(intent);
    }
    public void OpenCompany(){
        Intent intent = new Intent(this, CompanyActivity.class);
        startActivity(intent);
    }
    public void OpenVerify(){
        Intent intent = new Intent(this, ValidateActivity.class);
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