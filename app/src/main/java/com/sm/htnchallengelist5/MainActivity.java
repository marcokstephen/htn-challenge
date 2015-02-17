package com.sm.htnchallengelist5;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    static List<Person> attendees = new ArrayList<Person>();
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
}
