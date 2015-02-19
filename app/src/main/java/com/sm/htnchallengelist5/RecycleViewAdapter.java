package com.sm.htnchallengelist5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    List<Person> dataset;
    Context c;
    OnClickMode mode;

    public enum OnClickMode{
        ADD_TEAM_MEMBER, REMOVE_TEAM_MEMBER
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ImageView mImageView;
        public TextView nameTextView;
        public TextView emailTextView;
        public TextView skillsTextView;
        private OnClickMode doubleTapMode;
        private Context vhContext;
        private boolean doubleClick = false;
        private Person currentPerson;


        public ViewHolder(View v, OnClickMode clickMode, Context context) {
            super(v);
            v.setOnLongClickListener(this);
            v.setOnClickListener(this);
            this.doubleTapMode = clickMode;
            this.vhContext = context;
            mImageView = (ImageView)v.findViewById(R.id.ImageViewContact);
            nameTextView = (TextView)v.findViewById(R.id.TextViewName);
            emailTextView = (TextView)v.findViewById(R.id.TextViewEmail);
            skillsTextView = (TextView)v.findViewById(R.id.TextViewSkills);
        }

        @Override
        public boolean onLongClick(View v) {
            String jsonPersonString = v.getTag().toString();
            Person p = new Person(jsonPersonString);
            String phoneNumber = "tel:" + p.getPhone();
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse(phoneNumber));
            vhContext.startActivity(callIntent);
            return true;
        }

        @Override
        public void onClick(View v) {
            if (!doubleClick) {
                //this is the first time it is being clicked, set doubleClick to true and reset with a handler
                doubleClick = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleClick = false;
                        Log.d("OUTPUT", "Resetting doubleClick to false");
                    }
                }, 500);
            } else if (doubleTapMode == OnClickMode.ADD_TEAM_MEMBER) {
                //this is the second time it is being clicked, we can execute the double click command
                Log.d("OUTPUT", "Adding " + nameTextView.getText().toString() + " from team members");

                Toast.makeText(vhContext,"Adding " + nameTextView.getText().toString() + " to team", Toast.LENGTH_SHORT).show();
                String jsonPersonString = currentPerson.toString();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(vhContext);
                SharedPreferences.Editor editor = prefs.edit();
                String currentTeamString = prefs.getString(TeamMembers.PREFERENCE_TEAM_MEMBERS, "[]");
                try {
                    JSONArray currentTeamJsonArray = new JSONArray(currentTeamString);
                    JSONObject jsonPerson = new JSONObject(jsonPersonString);
                    currentTeamJsonArray.put(jsonPerson);
                    editor.putString(TeamMembers.PREFERENCE_TEAM_MEMBERS,currentTeamJsonArray.toString());
                    editor.commit();
                } catch (JSONException e){
                    e.printStackTrace();
                }

            } else {
                //this is the second time it is being clicked, we can execute the double click command
                Log.d("OUTPUT", "Removing " + nameTextView.getText().toString() + " from team members");
                Toast.makeText(vhContext,"Removing " + nameTextView.getText().toString() + " from team", Toast.LENGTH_SHORT).show();

                String currentPersonName = currentPerson.getName();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(vhContext);
                SharedPreferences.Editor editor = prefs.edit();
                String currentTeamString = prefs.getString(TeamMembers.PREFERENCE_TEAM_MEMBERS, "[]");
                try {
                    JSONArray currentTeamJsonArray = new JSONArray(currentTeamString);
                    for (int i = 0; i < currentTeamJsonArray.length(); i++){
                        JSONObject jsonObject = currentTeamJsonArray.getJSONObject(i);
                        if (jsonObject.getString("name").equals(currentPersonName)){
                            currentTeamJsonArray.remove(i);
                            TeamMembers.teamMemberList.remove(i);
                            break;
                        }
                    }
                    currentTeamString = currentTeamJsonArray.toString();
                    editor.putString(TeamMembers.PREFERENCE_TEAM_MEMBERS,currentTeamString);
                    editor.commit();
                } catch (JSONException e){
                    e.printStackTrace();
                }
                TeamMembers.notifyListChanged();
            }
        }
    }


    public RecycleViewAdapter(List<Person> attendees, OnClickMode mode, Context context){
        this.dataset = attendees;
        this.c = context;
        this.mode = mode;
    }

    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attendee, viewGroup,false);
        ViewHolder vh = new ViewHolder(v,mode,c);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mImageView.setImageDrawable(null);
        viewHolder.currentPerson = dataset.get(i);
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
