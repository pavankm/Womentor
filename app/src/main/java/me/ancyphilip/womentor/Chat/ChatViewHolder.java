package me.ancyphilip.womentor.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
<<<<<<< HEAD
import android.widget.LinearLayout;
=======
>>>>>>> 9450eff... linkedin changes
import android.widget.TextView;

import me.ancyphilip.womentor.R;

/**
 * Created by ancyphilip on 2/25/18.
 */

public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
<<<<<<< HEAD
    public TextView mMessage;
    public LinearLayout mContainer;
=======

>>>>>>> 9450eff... linkedin changes

    public ChatViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

<<<<<<< HEAD
        mMessage = itemView.findViewById(R.id.message);
        mContainer = itemView.findViewById(R.id.container);


=======
>>>>>>> 9450eff... linkedin changes
    }

    @Override
    public void onClick(View view) {


    }
}
