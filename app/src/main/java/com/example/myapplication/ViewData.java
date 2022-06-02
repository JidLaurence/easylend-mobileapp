package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewData extends AppCompatActivity {

    EditText viewData_fullname_ET, viewData_capital_ET, viewData_collected_ET, viewData_balance_ET, viewData_amount_ET, viewData_phoneNumber_ET;

    TextView viewData_date_TV;

    MyDatabaseHelper myDB = new MyDatabaseHelper(ViewData.this);

    String id, amountSTR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data);

        Bundle extras = getIntent().getExtras();
        id = extras.getString("id");

        viewData_fullname_ET = findViewById(R.id.viewData_fullname_ET);
        viewData_capital_ET = findViewById(R.id.viewData_capital_ET);
        viewData_collected_ET = findViewById(R.id.viewData_collected_ET);
        viewData_balance_ET = findViewById(R.id.viewData_balance_ET);
        viewData_amount_ET = findViewById(R.id.viewData_amount_ET);
        viewData_phoneNumber_ET = findViewById(R.id.viewData_phoneNumber_ET);

        viewData_date_TV = findViewById(R.id.viewData_date_TV);

        viewData_fullname_ET.setText(myDB.getDataFirstName(id) + " " + myDB.getDataLastName(id) + " " + myDB.getDataMiddleName(id)  + ".");
        viewData_capital_ET.setText(myDB.getDataCapital(id));
        viewData_collected_ET.setText(myDB.getDataCollected(id));
        viewData_balance_ET.setText(myDB.getDataBalance(id));
        viewData_date_TV.setText(myDB.getDataDay(id) + "/" + myDB.getDataMonth(id) + "/" + myDB.getDataYear(id));
        viewData_amount_ET.setText(myDB.getDataAmount(id));
        viewData_phoneNumber_ET.setText(myDB.getDataPhone(id));



        Button viewData_updateButton = findViewById(R.id.viewData_updateButton);
        viewData_updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amountSTR = viewData_amount_ET.getText().toString();
                myDB.upDateAmount(id, amountSTR);
                openMemberList();
            }
        });








    }

    public void openMemberList(){
        Intent intent = new Intent(this, MemberList.class);

        intent.putExtra("id", id);
        startActivity(intent);
    }
}