package com.example.telecomm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ChatActivity extends AppCompatActivity implements ContactListFragment.onButtonClickListener, SenderReceiverChatFragment.SendReceiveBack {
    private String number;
    View chatListFragment;
    View chatFragment;

    ChatContactListDataModel userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatListFragment = findViewById(R.id.ChatActivityContactListFrag);
        chatFragment = findViewById(R.id.ChatActivityChatFrag);
    }

    @Override
    protected void onResume() {
        super.onResume();
        number = getIntent().getStringExtra("Number");
        ContactListFragment frag = (ContactListFragment) getSupportFragmentManager().findFragmentById(R.id.ChatActivityContactListFrag);
        frag.setData(number,chatListFragment,chatFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.chatActivitySignOutButton:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,HomeScreen.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onButtonClick(ChatContactListDataModel c) {
        userData = c;
//        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.ChatActivityChatFrag)).commit();
//        getSupportFragmentManager().beginTransaction().remove(new ChatFragment()).commit();
        SenderReceiverChatFragment fr = new SenderReceiverChatFragment();
        Bundle b = new Bundle();
        b.putString("UserName",c.getUser_Name());
        b.putString("UserNumber",c.getUser_Number());
        b.putString("ProfileImage",c.getProfile_Image());
        fr.setArguments(b);
        getSupportFragmentManager().beginTransaction().add(R.id.Container,fr).addToBackStack(null).commit();
    }

    @Override
    public void onclickBackBtn() {
        chatListFragment.setVisibility(View.VISIBLE);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        chatListFragment.setVisibility(View.VISIBLE);
    }
}