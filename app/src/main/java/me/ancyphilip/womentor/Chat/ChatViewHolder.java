package me.ancyphilip.womentor.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.ancyphilip.womentor.R;

/**
 * Created by ancyphilip on 2/25/18.
 */

public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMessage;
    public LinearLayout mContainer;
    public TextView mSender;
    public ImageView mProfileImage;

    public ChatViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMessage = itemView.findViewById(R.id.message);
        mContainer = itemView.findViewById(R.id.container);
        mSender = itemView.findViewById(R.id.sender);
    }

    @Override
    public void onClick(View view) {


    }
}
