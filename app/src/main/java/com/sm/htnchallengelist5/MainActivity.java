package com.sm.htnchallengelist5;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    static List<Person> attendees = new ArrayList<Person>();
    static RecycleViewAdapter recycleViewAdapter;
    static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerViewMain);
        recyclerView.hasFixedSize();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        /*List<Skill> skillz = new ArrayList<Skill>();
        skillz.add(new Skill("Android",5));
        skillz.add(new Skill("Python",10));
        Person p = new Person("Stephen Marcok","http://google.ca","Waterloo","marcok.stephen@gmail.com","2893148444",0,0,skillz);
        attendees.add(p);
        p = new Person("Stephen Marcok","http://google.ca","Waterloo","marcok.stephen@gmail.com","2893148444",0,0,skillz);
        attendees.add(p);
        p = new Person("Stephen Marcok","http://google.ca","Waterloo","marcok.stephen@gmail.com","2893148444",0,0,skillz);
        attendees.add(p);
        p = new Person("Stephen Marcok","http://google.ca","Waterloo","marcok.stephen@gmail.com","2893148444",0,0,skillz);
        attendees.add(p);
        populateRecycler();*/

        new DownloadAttendees().execute();
    }

    public static void populateRecycler(){
        recycleViewAdapter = new RecycleViewAdapter(attendees);
        recyclerView.setAdapter(recycleViewAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
