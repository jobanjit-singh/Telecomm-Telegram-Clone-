package com.example.telecomm;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class AddDetails extends AppCompatActivity {
    ImageView fishImage,addDetailAddImage;
    EditText addDetailNameEdit,addDetailNumberEdit;
    CardView addDetailImgCard;
    Button addDetailNextBtn;
    int SELECT_PICTURE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);


        addDetailAddImage = findViewById(R.id.addDetailAddImageView);
        addDetailNameEdit = findViewById(R.id.addDetailNameEditText);
        addDetailNumberEdit = findViewById(R.id.addDetailNumberEditText);
        addDetailNextBtn = findViewById(R.id.addDetailNextBtn);
        addDetailImgCard = findViewById(R.id.addDetailImgCard);


        fishImage = findViewById(R.id.addDetailFishImage);
        Animation an = AnimationUtils.loadAnimation(AddDetails.this,R.anim.add_details_fish_image_anim);
        fishImage.startAnimation(an);

        String num = getIntent().getStringExtra("phoneNumber");
        addDetailNumberEdit.setText(num);

        addDetailAddImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bitmap p1 = ((BitmapDrawable) addDetailAddImage.getDrawable()).getBitmap();
                Bitmap emptyImage = BitmapFactory.decodeResource(getResources(),R.drawable.emptyimage);
                if(p1.getHeight()==emptyImage.getHeight() || p1.getWidth()==emptyImage.getWidth()){
                    imageChooser();
                }
            }
        });

        addDetailNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap p1 = ((BitmapDrawable)addDetailAddImage.getDrawable()).getBitmap();
                Bitmap empty = BitmapFactory.decodeResource(getResources(),R.drawable.emptyimage);
                if((p1.getWidth() == empty.getWidth() || p1.getHeight()==empty.getHeight()) || addDetailNameEdit.getText().toString().isEmpty()){
                    Toast.makeText(AddDetails.this, "Please fill the details", Toast.LENGTH_SHORT).show();
                }
                else{
                    Bitmap profileImage = ((BitmapDrawable)addDetailAddImage.getDrawable()).getBitmap();
                    addDetailNextBtn.setText("Please Wait...");
                    adddetail(profileImage,addDetailNameEdit.getText().toString(),addDetailNumberEdit.getText().toString());
                }
            }
        });
    }
    public void imageChooser(){
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i,"Choose the Profile Image"),SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_PICTURE){
                if(data != null){
                    Uri image = data.getData();
                    try {
                        addDetailAddImage.setImageBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(image)));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    public void adddetail(Bitmap pf,String userName,String userNumber){
        HashMap<String,Object> data = new HashMap<>();
        data.put("Profile Image",pf);
        data.put("User Name",userName);
        data.put("User Number",userNumber);

        FirebaseDatabase.getInstance().getReference().child("chat").child(userNumber).setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(AddDetails.this,AccountCreatedActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}