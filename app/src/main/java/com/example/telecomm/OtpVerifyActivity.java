package com.example.telecomm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class OtpVerifyActivity extends AppCompatActivity {
    EditText otpVerifyOtpEdittext;
    Button otpVerifyBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);
        otpVerifyOtpEdittext = findViewById(R.id.otpVerifyOtpEdittext);
        otpVerifyBtn = findViewById(R.id.otpVerifyBtn);
        String num = getIntent().getStringExtra("Number");
    }
}