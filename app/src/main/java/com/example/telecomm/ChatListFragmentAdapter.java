package com.example.telecomm;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ChatListFragmentAdapter extends RecyclerView.Adapter<ChatListFragmentAdapter.ChatListViewHolder>{

    Context context;
    List<ChatContactListDataModel> data;
    ChatListClick listener;
    public ChatListFragmentAdapter(Context c,List<ChatContactListDataModel> d,ChatListClick li){
        this.context = c;
        this.data = d;
        this.listener = li;
    }

    @NonNull
    @Override
    public ChatListFragmentAdapter.ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.chat_list_fragment_item_view,parent,false);
        return new ChatListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListFragmentAdapter.ChatListViewHolder holder, int position) {
        ChatContactListDataModel c = data.get(position);
        Glide.with(context).load(Uri.parse(c.getProfile_Image())).into(holder.chatListProfileImage);
        holder.chatListUserName.setText(c.getUser_Name());

        holder.chatListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickList(c);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class ChatListViewHolder extends RecyclerView.ViewHolder{
        ImageView chatListProfileImage;
        TextView chatListUserName;
        CardView chatListCardView;
        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            chatListProfileImage = itemView.findViewById(R.id.chat_list_fragment_profileImage);
            chatListUserName = itemView.findViewById(R.id.chat_list_fragment_userName);
            chatListCardView = itemView.findViewById(R.id.chat_list_fragment_card_view);
        }
    }
}
