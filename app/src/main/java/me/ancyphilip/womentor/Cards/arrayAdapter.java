package me.ancyphilip.womentor.Cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.ancyphilip.womentor.Cards.cards;
import me.ancyphilip.womentor.R;

/**
 * Created by ancyphilip on 2/24/18.
 */

public class arrayAdapter extends ArrayAdapter<cards> {

    public arrayAdapter(Context context, int resourceId, List<cards> items) {
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        cards card_item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);

        name.setText(card_item.getName());

        switch (card_item.getProfileImageUrl()) {
            case "default":
                Glide.with(convertView.getContext()).load(R.mipmap.ic_launcher).into(image);
                break;
            default:
                Glide.clear(image);
                Glide.with(convertView.getContext()).load(card_item.getProfileImageUrl()).into(image);
                break;
        }
        return convertView;

    }
}
