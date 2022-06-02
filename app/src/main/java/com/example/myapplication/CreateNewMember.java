package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class CreateNewMember extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_member);

        String create_new_member_string = "Registered";
        TextView create_new_member_registered_TV = findViewById(R.id.create_new_member_registered_TV);

        SpannableString create_new_member_registered_spannable_string = new SpannableString(create_new_member_string);

        ClickableSpan create_new_member_registered_clickable_span = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                openAddExistingMember();
            }
        };

        create_new_member_registered_spannable_string.setSpan(create_new_member_registered_clickable_span, 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        create_new_member_registered_spannable_string.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        create_new_member_registered_TV.setText(create_new_member_registered_spannable_string);
        create_new_member_registered_TV.setMovementMethod(LinkMovementMethod.getInstance());

        Spinner create_new_member_months_to_pay_spinner = findViewById(R.id.create_new_member_months_to_pay_spinner);
        Spinner create_new_member_capital_spinner = findViewById(R.id.create_new_member_capital_spinner);
        Spinner create_new_member_type_spinner = findViewById(R.id.create_new_member_type_spinner);

        ArrayAdapter<CharSequence> create_new_member_months_to_pay_spinner_adapter =  ArrayAdapter.createFromResource(this, R.array.months_to_pay, android.R.layout.simple_spinner_item);
        create_new_member_months_to_pay_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        create_new_member_months_to_pay_spinner.setAdapter(create_new_member_months_to_pay_spinner_adapter);

        ArrayAdapter<CharSequence> create_new_member_capital_spinner_adapter =  ArrayAdapter.createFromResource(this, R.array.capital, android.R.layout.simple_spinner_item);
        create_new_member_capital_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        create_new_member_capital_spinner.setAdapter(create_new_member_capital_spinner_adapter);

        ArrayAdapter<CharSequence> create_new_member_type_spinner_adapter =  ArrayAdapter.createFromResource(this, R.array.payment_type, android.R.layout.simple_spinner_item);
        create_new_member_type_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        create_new_member_type_spinner.setAdapter(create_new_member_type_spinner_adapter);



    }

    public void openAddExistingMember(){
        Intent intent = new Intent(this, AddExistingMember.class);
        startActivity(intent);
        finish();
    }
}