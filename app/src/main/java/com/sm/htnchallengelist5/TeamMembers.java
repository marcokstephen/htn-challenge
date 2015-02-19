package com.sm.htnchallengelist5;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TeamMembers extends Activity {

    public static final String PREFERENCE_TEAM_MEMBERS = "com.sm.htnchallengelist5.PREFERENCE_TEAM_MEMBERS";

    private SharedPreferences prefs;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private List<Person> teamMemberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_members);

        teamMemberList = new ArrayList<Person>();

        /*
            Initializing the RecyclerView
         */
        recyclerView = (RecyclerView)findViewById(R.id.RecyclerViewTeam);
        recyclerView.hasFixedSize();
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

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

        RecycleViewAdapter teamMemberAdapter = new RecycleViewAdapter(teamMemberList,RecycleViewAdapter.OnClickMode.REMOVE_TEAM_MEMBER, this);
        recyclerView.setAdapter(teamMemberAdapter);
    }
}
