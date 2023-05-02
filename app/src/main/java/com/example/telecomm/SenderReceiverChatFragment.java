package com.example.telecomm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SenderReceiverChatFragment extends Fragment {
    String ReceiverUserName,ReceiverProfileImage,ReceiverUserNumber,SenderUserNumber;
    ImageView SendReceiveBackBtn,SendReceiveProfileImage,SendReceiveSendBtn,SendReceiveCallBtn,SendReceiveDeleteChatBtn;
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
        SendReceiveCallBtn = getView().findViewById(R.id.SendReceiveCallBtn);
        SendReceiveDeleteChatBtn = getView().findViewById(R.id.SendReceiveDeleteChatBtn);

        SendReceiveDeleteChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.confirm);
                builder.setMessage(R.string.are_you_sure);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference("chat")
                                .child(SenderUserNumber)
                                .child("chats")
                                .child(ReceiverUserNumber)
                                .setValue("")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getContext(), "Chat is Deleted", Toast.LENGTH_SHORT).show();
                                        dialogInterface.dismiss();
                                    }
                                });
                    }
                });
                builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });

        SendReceiveCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+ReceiverUserNumber.substring(3)));
                startActivity(i);
            }
        });

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

        List<MessageDataModel> Messages = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("chat")
                .child(SenderUserNumber)
                .child("chats")
                .child(ReceiverUserNumber)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Messages.clear();
                        for(DataSnapshot data:snapshot.getChildren()){
                            Messages.add(new MessageDataModel(
                                    data.child("Message").getValue(String.class),
                                    data.child("Type").getValue(String.class)
                            ));
                        }
                        MessageAdapter adapter = new MessageAdapter(Messages,getContext());
                        LinearLayoutManager layout = new LinearLayoutManager(getContext());
                        layout.setStackFromEnd(true);
                        SendReceiveRecyclerView.setLayoutManager(layout);
                        SendReceiveRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(Messages.size()-1);
                        SendReceiveRecyclerView.scrollToPosition(Messages.size()-1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Error in fetching Chats", Toast.LENGTH_SHORT).show();
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
