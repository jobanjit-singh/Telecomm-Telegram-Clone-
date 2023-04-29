package com.example.telecomm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountCreatedActivity extends AppCompatActivity {
    ImageView account_created_cycle_img;
    Button account_created_start_btn;
    TextView account_created_alldone_txt;
    CardView account_created_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_created);

        account_created_cycle_img = findViewById(R.id.acoount_created_cycle_Image);
        account_created_start_btn = findViewById(R.id.account_created_start_chat_btn);
        account_created_alldone_txt = findViewById(R.id.account_created_alldone_txt);
        account_created_card = findViewById(R.id.account_created_card);

        account_created_cycle_img.startAnimation(AnimationUtils.loadAnimation(this,R.anim.account_created_cycle_animation));
        account_created_alldone_txt.startAnimation(AnimationUtils.loadAnimation(this,R.anim.account_created_alldone_text_anim));
        account_created_card.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splashanimtxt));

        account_created_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountCreatedActivity.this,ChatActivity.class);
                i.putExtra("Number",getIntent().getStringExtra("Number"));
                startActivity(i);
                finish();
            }
        });
    }
}