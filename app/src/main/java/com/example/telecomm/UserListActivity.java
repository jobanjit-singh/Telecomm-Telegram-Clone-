package com.example.telecomm;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListActivity extends AppCompatActivity {
    Bundle b;
    ArrayList<String> userNumbers;
    HashMap<String,String> myNumbers;
    final int REQUEST_CODE_PERMISSION = 200;
    private String userNumber;

    TextView userListNoUserFoundTxt;
    ProgressBar userListProgressBar;
    ListView userListListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        b = getIntent().getBundleExtra("UserNumber");
        userNumbers = b.getStringArrayList("Number Data");
        userNumber = getIntent().getStringExtra("User");
        myNumbers = new HashMap<>();

        userListListView = findViewById(R.id.userListListView);
        userListNoUserFoundTxt = findViewById(R.id.userListNoUserFoundText);
        userListProgressBar = findViewById(R.id.userListProgressBar);

        getPermission();
    }
    public void getPermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED){
            for(int i=0;i<userNumbers.size();i++){
                Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(userNumbers.get(i).substring(3)));
                String[] projection = {ContactsContract.PhoneLookup.NUMBER};
                String[] projection1 = {ContactsContract.PhoneLookup.DISPLAY_NAME};
                Cursor cur = this.getContentResolver().query(uri,projection,null,null,null);
                Cursor curName = this.getContentResolver().query(uri,projection1,null,null,null);
                if(cur!=null && curName!=null){
                    if(cur.moveToFirst() && curName.moveToFirst()){
                        myNumbers.put(curName.getString(0),userNumbers.get(i).substring(3));
                    }
                    cur.close();
                }
            }
            Log.d("Data is present","Hashmap is created");
            if(myNumbers.size()==0){
                userListProgressBar.setVisibility(View.GONE);
                userListNoUserFoundTxt.setVisibility(View.VISIBLE);
            }
            else{
                userListProgressBar.setVisibility(View.GONE);
                List<Map.Entry<String,String>> data = new ArrayList<>(myNumbers.entrySet());

                ArrayAdapter<Map.Entry<String,String>> adapter = new ArrayAdapter<Map.Entry<String,String>>(this,android.R.layout.simple_list_item_2,android.R.id.text1,data){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView txt1 = view.findViewById(android.R.id.text1);
                        TextView txt2 = view.findViewById(android.R.id.text2);
                        Map.Entry<String,String> entry = (Map.Entry<String, String>) getItem(position);
                        txt1.setText(entry.getKey());
                        txt2.setText(entry.getValue());
                        return view;
                    }
                };
                userListListView.setAdapter(adapter);
                userListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Map.Entry<String,String> data = (Map.Entry<String, String>) adapterView.getItemAtPosition(i);
                        String number = "+91"+data.getValue();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("chat");
                        ref.child(userNumber).child("chats").child(number).setValue("Hello");
                        ref.child(number).child("chats").child(userNumber).setValue("Hello");
                        Toast.makeText(UserListActivity.this, "Chat is Added", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(UserListActivity.this,ChatActivity.class);
                        in.putExtra("Number",userNumber);
                        startActivity(in);
                        finish();
                    }
                });
            }
        }
        else{
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CONTACTS},REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_PERMISSION){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                getPermission();
            }
            else{
                Toast.makeText(this, "Permission Denied Go To app Settings", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserListActivity.this,ChatActivity.class));
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(UserListActivity.this,ChatActivity.class);
        i.putExtra("Number",userNumber);
        startActivity(i);
        finish();

    }
}