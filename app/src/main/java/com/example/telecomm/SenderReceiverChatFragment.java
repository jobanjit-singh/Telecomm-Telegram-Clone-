package com.example.telecomm;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SenderReceiverChatFragment extends Fragment {
    String UserName,ProfileImage,UserNumber;
    ImageView SendReceiveBackBtn;
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
        UserName = getArguments().getString("UserName");
        UserNumber = getArguments().getString("UserNumber");
        ProfileImage = getArguments().getString("ProfileImage");

        SendReceiveBackBtn = getView().findViewById(R.id.SendReceiveBackButton);
        SendReceiveBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back.onclickBackBtn();
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
