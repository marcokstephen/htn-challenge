package com.sm.htnchallengelist5;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends Activity {

    static List<Person> attendees = new ArrayList<Person>();
    static List<Person> searchAttendees = new ArrayList<Person>(); //for use when searching
    static RecycleViewAdapter recycleViewAdapter;
    static RecyclerView recyclerView;
    static LinearLayoutManager mLayoutManager;
    private EditText searchEditText;
    private Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = this;

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerViewMain);
        recyclerView.hasFixedSize();
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        new DownloadAttendees().execute();

        searchEditText = (EditText) findViewById(R.id.EditTextSearch);
        searchEditText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
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
                recycleViewAdapter = new RecycleViewAdapter(searchAttendees);
                recyclerView.setAdapter(recycleViewAdapter);
            }
        });
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
            if (searchEditText.getVisibility() == View.GONE){ //begin search
                recycleViewAdapter = new RecycleViewAdapter(searchAttendees);
                recyclerView.setAdapter(recycleViewAdapter);
                searchEditText.setVisibility(View.VISIBLE);
            } else { //end search
                searchEditText.setVisibility(View.GONE);
                recycleViewAdapter = new RecycleViewAdapter(attendees);
                recyclerView.setAdapter(recycleViewAdapter);
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
