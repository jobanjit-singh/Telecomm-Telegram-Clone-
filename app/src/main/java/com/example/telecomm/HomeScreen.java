package com.example.telecomm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;

public class HomeScreen extends AppCompatActivity {
    CardView homeScreenImgCard;
    ImageView homeScreenLogo;
    Button homeScreenStartMsgBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        homeScreenImgCard = findViewById(R.id.homeScreenCard);
        homeScreenLogo = findViewById(R.id.homeScreenLogo);
        homeScreenStartMsgBtn = findViewById(R.id.homeScreenStartMsgBtn);
        Animation ani = AnimationUtils.loadAnimation(this,R.anim.splashanimtxt);
        homeScreenImgCard.startAnimation(ani);
        Animation aniimg = AnimationUtils.loadAnimation(this,R.anim.splashanimtxt);
        homeScreenLogo.startAnimation(aniimg);

        homeScreenStartMsgBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(HomeScreen.this, PhoneVerifyActivity.class);
                startActivity(i);
            }
        });
    }
}