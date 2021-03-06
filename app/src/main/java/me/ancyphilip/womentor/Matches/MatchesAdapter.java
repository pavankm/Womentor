package me.ancyphilip.womentor.Matches;

import android.content.Context;
import android.support.v7.widget.ListPopupWindow;
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

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolder> {
    private List<MatchesObject> matchesObjectList;
    private Context context;

    public MatchesAdapter(List<MatchesObject> matchesObjectList, Context context) {
        this.matchesObjectList = matchesObjectList;
        this.context = context;
    }

    @Override
    public MatchesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchesViewHolder rcv = new MatchesViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(MatchesViewHolder holder, int position) {
        holder.mMatchId.setText(matchesObjectList.get(position).getUserId());
        holder.mMatchName.setText(matchesObjectList.get(position).getName());
        Glide.with(context).load(matchesObjectList.get(position).getProfileImageUrl()).into(holder.mMatchImage);
    }

    @Override
    public int getItemCount() {
        return this.matchesObjectList.size();
    }
}
