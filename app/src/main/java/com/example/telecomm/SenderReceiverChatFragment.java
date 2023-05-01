package com.example.telecomm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SenderReceiverChatFragment extends Fragment {
    String ReceiverUserName,ReceiverProfileImage,ReceiverUserNumber,SenderUserNumber;
    ImageView SendReceiveBackBtn,SendReceiveProfileImage,SendReceiveSendBtn;
    TextView SendReceiveUserName;
    EditText SendReceiveMsgEdittext;
    RecyclerView SendReceiveRecyclerView;

    SendReceiveBack back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sender_receiver_chat_fragment,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SenderUserNumber= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        ReceiverUserName = getArguments().getString("UserName");
        ReceiverUserNumber = getArguments().getString("UserNumber");
        ReceiverProfileImage = getArguments().getString("ProfileImage");

        SendReceiveBackBtn = getView().findViewById(R.id.SendReceiveBackButton);
        SendReceiveProfileImage = getView().findViewById(R.id.SendReceiveProfileImage);
        SendReceiveUserName = getView().findViewById(R.id.SendReceiveUserName);
        SendReceiveMsgEdittext = getView().findViewById(R.id.SendReceiveMsgEditText);
        SendReceiveSendBtn = getView().findViewById(R.id.SendReceiveSendBtn);
        SendReceiveRecyclerView = getView().findViewById(R.id.SendReceiveRecyclerView);

        Glide.with(getContext()).load(Uri.parse(ReceiverProfileImage)).into(SendReceiveProfileImage);
        SendReceiveUserName.setText(ReceiverUserName);
        SendReceiveBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back.onclickBackBtn();
            }
        });

        SendReceiveSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SendReceiveMsgEdittext.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Enter the Message", Toast.LENGTH_SHORT).show();
                }
                else{
                    HashMap<String,String> Msg = new HashMap<>();
                    String msg = new EncryptDecryptData().dataEncryption(SendReceiveMsgEdittext.getText().toString());
                    Msg.put("Type","Send");
                    Msg.put("Message",msg);
                    FirebaseDatabase.getInstance().getReference("chat").child(SenderUserNumber).child("chats").child(ReceiverUserNumber).push()
                            .setValue(Msg)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    SendReceiveMsgEdittext.setText("");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    Msg.put("Type","Receive");
                    Msg.put("Message",msg);
                    FirebaseDatabase.getInstance().getReference("chat").child(ReceiverUserNumber).child("chats").child(SenderUserNumber).push()
                            .setValue(Msg)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    SendReceiveMsgEdittext.setText("");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }
    public interface SendReceiveBack{
        public void onclickBackBtn();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        back = (SendReceiveBack) context;
    }
}
