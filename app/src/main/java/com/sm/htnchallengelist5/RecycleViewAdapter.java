package com.sm.htnchallengelist5;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    List<Person> dataset;
    static Context c;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public ImageView mImageView;
        public TextView nameTextView;
        public TextView emailTextView;
        public TextView skillsTextView;
        public ViewHolder(View v) {
            super(v);
            v.setOnLongClickListener(this);
            mImageView = (ImageView)v.findViewById(R.id.ImageViewContact);
            nameTextView = (TextView)v.findViewById(R.id.TextViewName);
            emailTextView = (TextView)v.findViewById(R.id.TextViewEmail);
            skillsTextView = (TextView)v.findViewById(R.id.TextViewSkills);
        }

        @Override
        public boolean onLongClick(View v) {
            String phoneNumber = "tel:" + v.getTag();
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse(phoneNumber));
            c.startActivity(callIntent);
            return true;
        }
    }

    public RecycleViewAdapter(List<Person> attendees, Context context){
        this.dataset = attendees;
        this.c = context;
    }

    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attendee, viewGroup,false);
        v.setTag(dataset.get(i).getPhone());
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mImageView.setImageDrawable(null);
        viewHolder.nameTextView.setText(dataset.get(i).getName());
        viewHolder.emailTextView.setText(dataset.get(i).getEmail());

        String skillString = "";
        List<Skill> skills = dataset.get(i).getSkills();
        for (int j = 0; j < skills.size(); j++){
            Skill s = skills.get(j);
            String sString = s.getSname() + ": " + s.getRating();
            skillString += sString;
            if (j != skills.size() - 1){
                skillString += ", ";
            }
        }

        viewHolder.skillsTextView.setText(skillString);

        new AsyncImageDownloader(viewHolder.mImageView, i, dataset.get(i)).execute();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }



}
