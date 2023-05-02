package com.example.telecomm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MessageDataModel> Message;
    Context context;

    public MessageAdapter(List<MessageDataModel> m,Context c){
        this.Message = m;
        this.context = c;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = LayoutInflater.from(context).inflate(R.layout.message_send_item, parent, false);
            return new MessageViewHolder1(v);
        } else {
            View v1 = LayoutInflater.from(context).inflate(R.layout.message_receive_item, parent, false);
            return new MessageViewHolder2(v1);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageDataModel v = Message.get(position);
        String type = v.getType();
        Log.d("Data is Present",String.valueOf(holder.getItemViewType()));
        if(holder.getItemViewType()==0) {
            MessageViewHolder1 holder1 = (MessageViewHolder1) holder;
            holder1.SenderMsg.setText(new EncryptDecryptData().dataDecryption(v.getMessage()));
        }else{
            MessageViewHolder2 holder2 = (MessageViewHolder2) holder;
            holder2.ReceiverMsg.setText(new EncryptDecryptData().dataDecryption(v.getMessage()));
        }
    }

    @Override
    public int getItemCount() {
        return Message.size();
    }

    @Override
    public int getItemViewType(int position) {
        int val = -1;
        MessageDataModel c = Message.get(position);
        if(c.getType().equals("Send")){
            val = 0;
        }
        else{
            val = 1;
        }
        return val;
    }
    class MessageViewHolder1 extends RecyclerView.ViewHolder{
        TextView SenderMsg;
        public MessageViewHolder1(@NonNull View itemView) {
            super(itemView);
            SenderMsg = itemView.findViewById(R.id.SenderMsg);
        }
    }
    class MessageViewHolder2 extends RecyclerView.ViewHolder{
        TextView ReceiverMsg;
        public MessageViewHolder2(@NonNull View itemView) {
            super(itemView);
            ReceiverMsg = itemView.findViewById(R.id.ReceiverMsg);
        }
    }
}
