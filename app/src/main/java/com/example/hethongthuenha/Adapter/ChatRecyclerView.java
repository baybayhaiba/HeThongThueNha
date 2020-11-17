package com.example.hethongthuenha.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hethongthuenha.API.PersonAPI;
import com.example.hethongthuenha.Model.Chat;
import com.example.hethongthuenha.R;

import java.util.List;

public class ChatRecyclerView extends RecyclerView.Adapter<ChatRecyclerView.MyViewHolder> {

    private Context context;
    private List<Chat> chats;

    public ChatRecyclerView(Context context, List<Chat> chats) {
        this.context = context;
        this.chats = chats;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvChat;
        private LinearLayout linearChat;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChat=itemView.findViewById(R.id.tv_custom_chat);
            linearChat=itemView.findViewById(R.id.linear_custom_chat);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.custom_chat,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Chat chat=chats.get(position);
        holder.tvChat.setText(chat.getText());

        String fromUser= PersonAPI.getInstance().getEmail();
        String fromUserChat=chat.getFrom_email_person();



        if(!fromUser.equals(fromUserChat))
        {
            holder.linearChat.setGravity(Gravity.LEFT);
            holder.tvChat.setBackgroundResource(R.drawable.border_chat_left);
        }
        else{
            holder.linearChat.setGravity(Gravity.RIGHT);
            holder.tvChat.setBackgroundResource(R.drawable.border_chat_right);
        }

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}
