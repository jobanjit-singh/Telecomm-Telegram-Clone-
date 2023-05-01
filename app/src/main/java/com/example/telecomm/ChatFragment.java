package com.example.telecomm;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatFragment extends Fragment {
    ChatContactListDataModel UserData;
    View chatListFragment;
    LinearLayout chatFragHomeLayout;
    CardView cardViewChatFrag;
    String userName = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView chatFragImg = getView().findViewById(R.id.chatFragmentImg);
        TextView chatFragText = getView().findViewById(R.id.chatFragmentText);
        String user = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        FirebaseDatabase.getInstance().getReference("chat").child(user).child("Profile Image").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glide.with(getContext()).load(Uri.parse(snapshot.getValue(String.class))).into(chatFragImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error in fetching Profile Image", Toast.LENGTH_SHORT).show();
                Glide.with(getContext()).load(R.drawable.telecomm).into(chatFragImg);
            }
        });
        FirebaseDatabase.getInstance().getReference("chat").child(user).child("User Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatFragText.setText(new EncryptDecryptData().dataDecryption(snapshot.getValue(String.class)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error in fetching User Name", Toast.LENGTH_SHORT).show();
                chatFragText.setText(R.string.app_name);
            }
        });
    }
}