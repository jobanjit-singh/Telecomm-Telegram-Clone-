package com.example.telecomm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PhoneVerifyActivity extends AppCompatActivity {
    EditText phoneVerifyMobileNumber;
    Button phoneVerifyNextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverify);
    }
}