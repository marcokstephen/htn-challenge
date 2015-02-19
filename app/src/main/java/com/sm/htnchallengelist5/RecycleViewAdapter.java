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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sm.htnchallengelist5.Downloaders.AsyncImageDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    /*
        This enum is used to distinguish between which RecyclerView the adapter is
        being used for. ADD_TEAM_MEMBER means that the adapter is being used for the
        RecyclerView in the MainActivity, and REMOVE_TEAM_MEMBER means that it's being used
        for the RecyclerView in the TeamActivity (where doubleClicking removes a member
        from the team)
     */
    public enum OnClickMode{
        ADD_TEAM_MEMBER, REMOVE_TEAM_MEMBER
    }

    List<Person> dataset;
    Context c;
    OnClickMode mode;

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

        /*
            Starts a telephone callIntent with the person's number
         */
        @Override
        public boolean onLongClick(View v) {
            String phoneNumber = "tel:" + currentPerson.getPhone();
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse(phoneNumber));
            vhContext.startActivity(callIntent);
            return true;
        }

        /*
            Double tapping either adds the person to the team, or removes
            the person from the team (depending on what mode is set to)

            We determine a doubleClick by checking if onClick is called twice within
            a 500ms timeframe
         */
        @Override
        public void onClick(View v) {
            if (!doubleClick) {
                /*
                    This is the first time it is being clicked
                    Set doubleClick to true and reset with a handler
                 */
                doubleClick = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleClick = false;
                        Log.d("OUTPUT", "Resetting doubleClick to false");
                    }
                }, 500);

            } else if (doubleTapMode == OnClickMode.ADD_TEAM_MEMBER) {
                /*
                    This is the second time that the view is being clicked

                    We can add the currentPerson to the team by storing them
                    in SharedPreferences

                    First we get the current SharedPrefence and convert it to a
                    JSONArray. We get the person we are adding, convert them to a
                    JSONObject and add that Object to the Array. Then we convert the
                    JSONArray back to a string and store the updated String in
                    SharedPreferences.
                 */
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

            } else if (doubleTapMode == OnClickMode.REMOVE_TEAM_MEMBER) {
                //this is the second time it is being clicked
                //we can remove the team member from SharedPreferences
                Log.d("OUTPUT", "Removing " + nameTextView.getText().toString() + " from team members");
                Toast.makeText(vhContext,"Removing " + nameTextView.getText().toString() + " from team", Toast.LENGTH_SHORT).show();

                String currentPersonName = currentPerson.getName();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(vhContext);
                SharedPreferences.Editor editor = prefs.edit();
                String currentTeamString = prefs.getString(TeamMembers.PREFERENCE_TEAM_MEMBERS, "[]");
                try {
                    JSONArray currentTeamJsonArray = new JSONArray(currentTeamString);
                    for (int i = 0; i < currentTeamJsonArray.length(); i++){
                        /*
                            Iterating through the SharedPreference string to find the
                            specific person that needs to be removed. Then they are
                            removed from the SharedPreference and the TeamMembers.teamMemberList

                            After removing from the list we call notifyDataSetChanged to update
                            the RecyclerView.
                         */
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
                TeamMembers.teamMemberAdapter.notifyDataSetChanged();
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

        //Converting the List<Skill> to a string that can be placed within a TextView
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

        /*
            Downloading the image in an AsyncTask and placing it in the view when complete.
            The AsyncTask will take care of updating the view
         */
        new AsyncImageDownloader(viewHolder.mImageView, i, dataset.get(i), mode).execute();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
