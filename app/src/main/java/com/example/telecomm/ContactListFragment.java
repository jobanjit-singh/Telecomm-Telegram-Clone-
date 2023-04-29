package com.example.telecomm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactListFragment extends Fragment {
    Button contactFragAddChatBtn;
    RecyclerView contactFragRecyclarView;
    private ArrayList<String> numbers = new ArrayList<>();
    private String userNumber="";
    private ArrayList<ChatContactListDataModel> chatList = new ArrayList<>();
    private ArrayList<String> chatNumbers = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_list, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactFragAddChatBtn = getView().findViewById(R.id.ContactFragAddChatBtn);
        contactFragRecyclarView = getView().findViewById(R.id.ContactListFragRecyclerView);
        contactFragAddChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserNumbers();
                contactFragAddChatBtn.setText("Wait..");
            }
        });


    }
    public void getUserNumbers(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("chat");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    numbers.add(data.getKey());
                }
                Log.d("Data","Data is come");
                contactFragAddChatBtn.setText("+");
                Intent i = new Intent(getContext(),UserListActivity.class);
                Bundle numberData = new Bundle();
                numberData.putStringArrayList("Number Data",numbers);
                i.putExtra("UserNumber",numberData);
                i.putExtra("User",userNumber);
                startActivity(i);
                getActivity().finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error in Fetching Numbers", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setData(String n){
        userNumber = n;

        FirebaseDatabase.getInstance().getReference().child("chat")
                .child(userNumber).child("chats")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data : snapshot.getChildren()) {
                            chatNumbers.add(data.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Error in fetching Chats", Toast.LENGTH_SHORT).show();
                    }
                });

        //Get User Details for contact list

    }
}