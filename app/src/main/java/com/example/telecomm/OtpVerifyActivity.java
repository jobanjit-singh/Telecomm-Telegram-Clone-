package com.example.telecomm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerifyActivity extends AppCompatActivity {
    EditText otpVerifyOtpEdittext;
    TextView otpVerifyDesc;
    Button otpVerifyBtn;
    FirebaseAuth auth;
    String verificationCode;
    CustomLoadingDialog d;
    String num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        auth = FirebaseAuth.getInstance();
        d = new CustomLoadingDialog(this);

        otpVerifyDesc = findViewById(R.id.otpVerifyDesc);
        otpVerifyOtpEdittext = findViewById(R.id.otpVerifyOtpEdittext);
        otpVerifyBtn = findViewById(R.id.otpVerifyBtn);

        num = getIntent().getStringExtra("Number");

        otpVerifyDesc.setText(otpVerifyDesc.getText().toString()+" "+num);

        sendVerificationCode("+91"+num);
        otpVerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otpVerifyOtpEdittext.getText().toString().isEmpty()){
                    Toast.makeText(OtpVerifyActivity.this, "Enter the OTP", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("this","Button Clicked");
                    verifyCode(otpVerifyOtpEdittext.getText().toString());
                }
            }
        });
    }
    public void sendVerificationCode(String num){
        Log.d("this","sendVerification Code Method");
        d.showDialog();
        PhoneAuthOptions phoneauth = PhoneAuthOptions.newBuilder().
                setPhoneNumber(num).
                setTimeout(60L, TimeUnit.SECONDS).
                setActivity(this).
                setCallbacks(callbacks).build();
                PhoneAuthProvider.verifyPhoneNumber(phoneauth);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.d("this","OnverificationComplleted Method");
            String otp = otpVerifyOtpEdittext.getText().toString();
            verifyCode(otp);
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.d("this","oncode Sent method");
            d.hideDialog();
            verificationCode = s;
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d("this","on verification Failed method");
            d.hideDialog();
            Toast.makeText(OtpVerifyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    public void verifyCode(String otp){
        Log.d("this","verify Code Method");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,otp);
        signInWithOTP(credential);
    }
    public void signInWithOTP(PhoneAuthCredential credential) {
        Log.d("this", "signInWithOtp Method");
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent i = new Intent(OtpVerifyActivity.this,AddDetails.class);
                    i.putExtra("phoneNumber","+91"+num);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(OtpVerifyActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OtpVerifyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}