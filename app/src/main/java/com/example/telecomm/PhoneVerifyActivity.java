package com.example.telecomm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

public class PhoneVerifyActivity extends AppCompatActivity {
    EditText phoneVerifyMobileNumber;
    Button phoneVerifyNextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverify);
        phoneVerifyNextBtn = findViewById(R.id.phoneVerifyNextBtn);
        phoneVerifyMobileNumber = findViewById(R.id.phoneVerifyMobileNumberEdittext);
        phoneVerifyNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOtpVerify();
            }
        });
    }
    public void toOtpVerify(){
        if(phoneVerifyMobileNumber.getText().toString().isEmpty()){
            Toast.makeText(this, "Field is Empty", Toast.LENGTH_SHORT).show();
        }
        else if(phoneVerifyMobileNumber.getText().length()<10){
            Toast.makeText(this, "Kindly Enter Correct Mobile Number", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent i = new Intent(PhoneVerifyActivity.this,OtpVerifyActivity.class);
            String num = phoneVerifyMobileNumber.getText().toString();
            i.putExtra("Number",num);
            startActivity(i);
            finish();
        }
    }
}