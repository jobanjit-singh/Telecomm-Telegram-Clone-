package com.example.telecomm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ChatActivity extends AppCompatActivity {
    private String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        number = getIntent().getStringExtra("Number");

        number = "+918727864043";
        ContactListFragment frag = (ContactListFragment) getSupportFragmentManager().findFragmentById(R.id.ChatActivityContactListFrag);
        frag.setData(number);
    }
}