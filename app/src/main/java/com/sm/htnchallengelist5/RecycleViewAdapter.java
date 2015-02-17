package com.sm.htnchallengelist5;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    List<Person> dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView nameTextView;
        public TextView emailTextView;
        public TextView skillsTextView;
        public ViewHolder(View v) {
            super(v);
            mImageView = (ImageView)v.findViewById(R.id.ImageViewContact);
            nameTextView = (TextView)v.findViewById(R.id.TextViewName);
            emailTextView = (TextView)v.findViewById(R.id.TextViewEmail);
            skillsTextView = (TextView)v.findViewById(R.id.TextViewSkills);
        }
    }

    public RecycleViewAdapter(List<Person> attendees){
        this.dataset = attendees;
    }

    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attendee, viewGroup,false);
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
            skillString += sString + ", ";
        }

        viewHolder.skillsTextView.setText(skillString);

        new AsyncImageDownloader(viewHolder.mImageView, i, dataset.get(i)).execute();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }



}
