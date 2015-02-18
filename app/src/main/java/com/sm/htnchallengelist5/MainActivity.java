package com.sm.htnchallengelist5;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends Activity {

    static List<Person> attendees = new ArrayList<Person>();
    static List<Person> datasetBackup; //for use when searching
    static RecycleViewAdapter recycleViewAdapter;
    static RecyclerView recyclerView;
    static LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerViewMain);
        recyclerView.hasFixedSize();
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        new DownloadAttendees().execute();
    }

    public static void populateRecycler(){
        recycleViewAdapter = new RecycleViewAdapter(attendees);
        recyclerView.setAdapter(recycleViewAdapter);
    }

    public static void sortData(int method){
        //method 1 = alphabetical, method 2 = Distance, method 3 = Skill
        if (method == 1){
            Collections.sort(attendees, new Comparator<Person>() {
                @Override
                public int compare(Person lhs, Person rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });
        } else if (method == 2){
            Collections.sort(attendees, new Comparator<Person>() {
                @Override
                public int compare(Person lhs, Person rhs) {
                    return lhs.getDistanceFromUw() - rhs.getDistanceFromUw();
                }
            });
        } else if (method == 3){
            Collections.sort(attendees, new Comparator<Person>() {
                @Override
                public int compare(Person lhs, Person rhs) {
                    return lhs.getFirstSkillString().compareTo(rhs.getFirstSkillString());
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort_alpha){
            sortData(1);
            recycleViewAdapter.notifyDataSetChanged();
        } else if (id == R.id.sort_distance){
            sortData(2);
            recycleViewAdapter.notifyDataSetChanged();
        } else if (id == R.id.sort_skill){
            sortData(3);
            recycleViewAdapter.notifyDataSetChanged();
        } else if (id == R.id.menu_search){
            EditText searchEditText = (EditText) findViewById(R.id.EditTextSearch);
            if (searchEditText.getVisibility() == View.GONE){
                datasetBackup = new ArrayList<Person>(attendees); //TODO:
                searchEditText.setVisibility(View.VISIBLE);
            } else {
                searchEditText.setVisibility(View.GONE);
                attendees = new ArrayList<Person>(datasetBackup);
                recycleViewAdapter.notifyDataSetChanged(); //resetting to full results
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
