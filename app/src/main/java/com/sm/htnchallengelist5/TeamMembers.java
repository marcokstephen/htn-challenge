package com.sm.htnchallengelist5;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TeamMembers extends Activity {

    public static final String PREFERENCE_TEAM_MEMBERS = "com.sm.htnchallengelist5.PREFERENCE_TEAM_MEMBERS";

    public static LinearLayoutManager mLayoutManager;
    public static List<Person> teamMemberList;
    public static RecycleViewAdapter teamMemberAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_members);

        teamMemberList = new ArrayList<Person>();

        /*
            Initializing the RecyclerView
         */
        RecyclerView recyclerView;
        recyclerView = (RecyclerView)findViewById(R.id.RecyclerViewTeam);
        recyclerView.hasFixedSize();
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        /*
            Initializing the SharedPreferences, and extracting the current team
            members from the SharedPreference (as a string). That String is converted
            to a JSONArray, and we iterate through that array to reconstruct People and
            add them to the dataset (teamMemberList).
         */
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String teamMembersJsonString = prefs.getString(PREFERENCE_TEAM_MEMBERS, "[]");
        JSONArray teamMembersJsonArray;
        try{
            teamMembersJsonArray = new JSONArray(teamMembersJsonString);
            for (int i = 0; i < teamMembersJsonArray.length(); i++){
                JSONObject teamMemberJsonObject = teamMembersJsonArray.getJSONObject(i);
                teamMemberList.add(new Person(teamMemberJsonObject));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        //Ties the recyclerView to the teamMemberList
        teamMemberAdapter = new RecycleViewAdapter(teamMemberList,RecycleViewAdapter.OnClickMode.REMOVE_TEAM_MEMBER, this);
        recyclerView.setAdapter(teamMemberAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
