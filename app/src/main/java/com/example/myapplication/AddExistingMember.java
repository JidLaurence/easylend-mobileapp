package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

public class AddExistingMember extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_existing_member);

        String add_existing_member_not_registered_String = "Not Registered";
        TextView add_existing_member_not_registered_TV = findViewById(R.id.add_existing_member_not_registered_TV);

        SpannableString add_existing_member_not_registered_spannable_string = new SpannableString(add_existing_member_not_registered_String);
        ClickableSpan add_existing_member_not_registered_clickable_span = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                openCreateNewMember();
            }
        };

        add_existing_member_not_registered_spannable_string.setSpan(add_existing_member_not_registered_clickable_span, 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        add_existing_member_not_registered_spannable_string.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        add_existing_member_not_registered_TV.setText(add_existing_member_not_registered_spannable_string);
        add_existing_member_not_registered_TV.setMovementMethod(LinkMovementMethod.getInstance());


    }

    public void openCreateNewMember(){
        Intent intent = new Intent(this, CreateNewMember.class);
        startActivity(intent);
        finish();
    }
}