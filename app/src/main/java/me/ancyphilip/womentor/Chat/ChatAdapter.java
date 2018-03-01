package me.ancyphilip.womentor.Chat;

import android.content.Context;
<<<<<<< HEAD
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
=======
import android.support.v7.widget.RecyclerView;
>>>>>>> 9450eff... linkedin changes
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

<<<<<<< HEAD
=======
import com.bumptech.glide.Glide;

>>>>>>> 9450eff... linkedin changes
import java.util.List;

import me.ancyphilip.womentor.R;

/**
 * Created by ancyphilip on 2/25/18.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
<<<<<<< HEAD
    private List<ChatObject> chatList;
    private Context context;

    public ChatAdapter(List<ChatObject> chatList, Context context) {
        this.chatList = chatList;
=======
    private List<ChatObject> chatObjectList;
    private Context context;

    public ChatAdapter(List<ChatObject> chatObjectList, Context context) {
        this.chatObjectList = chatObjectList;
>>>>>>> 9450eff... linkedin changes
        this.context = context;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
<<<<<<< HEAD
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, null, false);
=======
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, null, false);
>>>>>>> 9450eff... linkedin changes
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ChatViewHolder rcv = new ChatViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
<<<<<<< HEAD
        holder.mMessage.setText(chatList.get(position).getMessage());
        if (chatList.get(position).getCurrentUser()) {
            holder.mMessage.setGravity(Gravity.END);
            holder.mMessage.setTextColor(Color.parseColor("#404040"));
            holder.mContainer.setBackgroundColor(Color.parseColor("#F4F4F4"));
        } else {
            holder.mMessage.setGravity(Gravity.START);
            holder.mMessage.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mContainer.setBackgroundColor(Color.parseColor("#2DB4C8"));
        }
=======
>>>>>>> 9450eff... linkedin changes

    }

    @Override
    public int getItemCount() {
<<<<<<< HEAD
        return this.chatList.size();
=======
        return this.chatObjectList.size();
>>>>>>> 9450eff... linkedin changes
    }
}
