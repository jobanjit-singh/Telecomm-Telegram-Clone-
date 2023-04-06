package com.example.telecomm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

public class SplashScreen extends AppCompatActivity {

    ImageView splashImage;
    TextView splashTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        splashImage = findViewById(R.id.splashImage);
        splashTxt = findViewById(R.id.splashTxt);

        Animation aniImage = AnimationUtils.loadAnimation(this,R.anim.splashanimimage);
        splashImage.startAnimation(aniImage);
        Animation aniTxt = AnimationUtils.loadAnimation(this,R.anim.splashanimtxt);
        splashTxt.startAnimation(aniTxt);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },3000);
    }
}