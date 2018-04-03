package me.ancyphilip.womentor.Cards;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.ancyphilip.womentor.R;

/**
 * Created by ancyphilip on 2/24/18.
 */

public class arrayAdapter extends ArrayAdapter<Card> {

    public arrayAdapter(Context context, int resourceId, List<Card> items) {
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Card card_item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.card_name);
        ImageView image = (ImageView) convertView.findViewById(R.id.card_image);

        TextView location = (TextView) convertView.findViewById(R.id.card_location);
        TextView jobTitle = (TextView) convertView.findViewById(R.id.card_job_title);
        TextView company = (TextView) convertView.findViewById(R.id.card_company);
        TextView skills = (TextView) convertView.findViewById(R.id.card_skills);

        name.setText(card_item.getName());
        if (card_item.getLocation() != null) {
            location.setText(card_item.getLocation());
            location.setVisibility(View.VISIBLE);
        }
        if (card_item.getCompany() != null) {
            company.setText(card_item.getCompany());
            company.setVisibility(View.VISIBLE);
        }
        if (card_item.getJobTitle() != null) {
            jobTitle.setText(card_item.getJobTitle());
            jobTitle.setVisibility(View.VISIBLE);
        }
        if (card_item.getSkills() != null) {
            skills.setText(TextUtils.join(", ", card_item.getSkills().toArray()));
            skills.setVisibility(View.VISIBLE);
        }


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
