package com.example.telecomm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactListFragment extends Fragment implements ChatListClick {
    Button contactFragAddChatBtn;
    RecyclerView contactFragRecyclarView;
    ProgressBar contactFragProgressBar;
    View chatListFragment;
    View chatFrag;
    onButtonClickListener mlistener;
    private ChatListFragmentAdapter adapter;
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
        contactFragProgressBar = getView().findViewById(R.id.ContactFragProgressBar);
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
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    numbers.add(data.getKey());
                }
                Log.d("Data","Data is come");
                contactFragAddChatBtn.setText("+");
                Bundle numberData = new Bundle();
                numberData.putStringArrayList("Number Data",numbers);
                Intent i = new Intent(getContext(),UserListActivity.class);
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
    public void setData(String n,View chatListFragment,View ChatFrag){
        userNumber = n;
        this.chatListFragment = chatListFragment;
        this.chatFrag = ChatFrag;
        FirebaseDatabase.getInstance().getReference().child("chat")
                .child(userNumber).child("chats")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatNumbers.clear();
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
        adapter = new ChatListFragmentAdapter(getContext(),chatList,this);
        contactFragRecyclarView.setLayoutManager(new LinearLayoutManager(getContext()));
        contactFragRecyclarView.setAdapter(adapter);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("chat");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for(DataSnapshot data:snapshot.getChildren()){
                    if(chatNumbers.contains(data.getKey())){
                        ChatContactListDataModel c = new ChatContactListDataModel(
                                new EncryptDecryptData().dataDecryption(data.child("User Name").getValue(String.class)),
                                data.child("Profile Image").getValue(String.class),
                                new EncryptDecryptData().dataDecryption(data.child("User Number").getValue(String.class))
                        );
                        chatList.add(c);
                    }
                }
                Log.d("Data is Present","List Chat"+String.valueOf(chatList.size()));
                adapter.notifyDataSetChanged();
                contactFragProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error is fetching contacts details", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public void onClickList(ChatContactListDataModel c) {
        mlistener.onButtonClick(c);
        chatListFragment.setVisibility(View.GONE);
    }
    public interface onButtonClickListener{
        void onButtonClick(ChatContactListDataModel c);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener = (onButtonClickListener) context;
    }
}