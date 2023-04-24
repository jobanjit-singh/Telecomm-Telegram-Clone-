package com.example.telecomm;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddDetails extends AppCompatActivity {
    ImageView fishImage,addDetailAddImage;
    EditText addDetailNameEdit,addDetailNumberEdit;
    Button addDetailNextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);


        addDetailAddImage = findViewById(R.id.addDetailAddImageView);
        addDetailNameEdit = findViewById(R.id.addDetailNameEditText);
        addDetailNumberEdit = findViewById(R.id.addDetailNumberEditText);
        addDetailNextBtn = findViewById(R.id.addDetailNextBtn);

        fishImage = findViewById(R.id.addDetailFishImage);
        Animation an = AnimationUtils.loadAnimation(AddDetails.this,R.anim.add_details_fish_image_anim);
        fishImage.startAnimation(an);
    }
}