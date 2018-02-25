package me.ancyphilip.womentor.Chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import me.ancyphilip.womentor.R;

/**
 * Created by ancyphilip on 2/25/18.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private List<ChatObject> chatObjectList;
    private Context context;

    public ChatAdapter(List<ChatObject> chatObjectList, Context context) {
        this.chatObjectList = chatObjectList;
        this.context = context;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ChatViewHolder rcv = new ChatViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.chatObjectList.size();
    }
}
