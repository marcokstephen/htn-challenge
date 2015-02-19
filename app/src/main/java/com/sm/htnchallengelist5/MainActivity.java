package com.sm.htnchallengelist5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.sm.htnchallengelist5.Downloaders.DownloadAttendees;
import com.sm.htnchallengelist5.RecyclerFastScroll.vertical.VerticalRecyclerViewFastScroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends Activity {

    /*
        This options that can be used to sort the list of attendees
        Currently supported: Alphabetical, Distance to UW, Primary Skill
     */
    public enum SortMethod{
        ALPHABETICAL, DISTANCE, SKILL
    }

    public static List<Person> attendees = new ArrayList<Person>();
    public static List<Person> searchAttendees = new ArrayList<Person>(); //for use when searching
    public static RecycleViewAdapter recycleViewAdapter;
    public static RecyclerView recyclerView;
    public static LinearLayoutManager mLayoutManager;
    private EditText searchEditText;
    private static Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = this;

        /*
            Initializing the RecyclerView
         */
        recyclerView = (RecyclerView)findViewById(R.id.RecyclerViewMain);
        recyclerView.hasFixedSize();
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        /*
            Pairing the Fast Scroller to the RecyclerView
         */
        VerticalRecyclerViewFastScroller fastScroller = (VerticalRecyclerViewFastScroller)findViewById(R.id.fast_scroller);
        fastScroller.setRecyclerView(recyclerView);
        recyclerView.setOnScrollListener(fastScroller.getOnScrollListener());

        /*
            Initiating the download of attendees from the Firebase source
         */
        new DownloadAttendees().execute();

        /*
            Initializing the Search EditText (becomes visible when search button is pressed)
         */
        searchEditText = (EditText) findViewById(R.id.EditTextSearch);
        searchEditText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            //Refresh the list of search results whenever the search string is modified
            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                searchAttendees = new ArrayList<Person>();
                if (search.length() != 0) {
                    for (int i = 0; i < attendees.size(); i++) {
                        if (attendees.get(i).getName().contains(search)) {
                            searchAttendees.add(attendees.get(i));
                        }
                    }
                }
                recycleViewAdapter = new RecycleViewAdapter(searchAttendees, RecycleViewAdapter.OnClickMode.ADD_TEAM_MEMBER, c);
                recyclerView.setAdapter(recycleViewAdapter);
            }
        });
    }

    /*
        Called by the AsyncImageDownloader after the download is complete
        Ties the RecyclerView to the downloaded data
     */
    public static void populateRecycler(){
        recycleViewAdapter = new RecycleViewAdapter(attendees, RecycleViewAdapter.OnClickMode.ADD_TEAM_MEMBER, c);
        recyclerView.setAdapter(recycleViewAdapter);
    }

    /*
        Sorts the dataset according to the specified mode
        This method is called from within onOptionsMenuSelected
     */
    public static void sortData(SortMethod method){
        if (method == SortMethod.ALPHABETICAL){
            Collections.sort(attendees, new Comparator<Person>() {
                @Override
                public int compare(Person lhs, Person rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });
        } else if (method == SortMethod.DISTANCE){
            Collections.sort(attendees, new Comparator<Person>() {
                @Override
                public int compare(Person lhs, Person rhs) {
                    return lhs.getDistanceFromUw() - rhs.getDistanceFromUw();
                }
            });
        } else if (method == SortMethod.SKILL){
            Collections.sort(attendees, new Comparator<Person>() {
                @Override
                public int compare(Person lhs, Person rhs) {
                    return lhs.getFirstSkillString().compareTo(rhs.getFirstSkillString());
                }
            });
        }
    }

    /*
        Used for specifying the sort method, also used for activating
        and deactivating the search functionality, as well as to open
        the TeamMembers activity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort_alpha){
            sortData(SortMethod.ALPHABETICAL);
            recycleViewAdapter.notifyDataSetChanged();
        } else if (id == R.id.sort_distance){
            sortData(SortMethod.DISTANCE);
            recycleViewAdapter.notifyDataSetChanged();
        } else if (id == R.id.sort_skill){
            sortData(SortMethod.SKILL);
            recycleViewAdapter.notifyDataSetChanged();
        } else if (id == R.id.menu_search){
            //The search button acts as a toggle for the EditText
            //The onEditTextChanged method is within this activity's onCreate
            if (searchEditText.getVisibility() == View.GONE){ //begin search
                recycleViewAdapter = new RecycleViewAdapter(searchAttendees,RecycleViewAdapter.OnClickMode.ADD_TEAM_MEMBER, this);
                recyclerView.setAdapter(recycleViewAdapter);
                searchEditText.setVisibility(View.VISIBLE);
            } else { //end search
                searchEditText.setVisibility(View.GONE);
                recycleViewAdapter = new RecycleViewAdapter(attendees,RecycleViewAdapter.OnClickMode.ADD_TEAM_MEMBER, this);
                recyclerView.setAdapter(recycleViewAdapter);
            }
        } else if (id == R.id.menu_group){
            Intent teamIntent = new Intent(this, TeamMembers.class);
            startActivity(teamIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
