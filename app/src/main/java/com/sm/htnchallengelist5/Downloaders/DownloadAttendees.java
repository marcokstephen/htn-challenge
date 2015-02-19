package com.sm.htnchallengelist5.Downloaders;

import android.os.AsyncTask;

import com.sm.htnchallengelist5.MainActivity;
import com.sm.htnchallengelist5.Person;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
    This class is used to download the JSON data from Firebase
    and convert the data to a List<Persons> found in MainActivity
 */

public class DownloadAttendees extends AsyncTask<Void,Void,Void> {

    private static final String URL = "https://htn15-interviews.firebaseio.com/.json";
    String output;

    @Override
    protected Void doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(URL);
        try{
            HttpResponse httpResponse = httpClient.execute(request);
            output = inputStreamToString(httpResponse.getEntity().getContent()).toString();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /*
        Initialize the list of Persons in MainActivity with the downloaded data.
        Sorts the data afterwards alphabetically, and then calls the
        populateRecycler() method to tie the RecyclerView to the data.
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.attendees = new ArrayList<Person>();

        try {
            JSONObject response = new JSONObject(output);
            JSONArray jsonUsers = response.getJSONArray("users");
            for (int i = 0; i < jsonUsers.length(); i++) {
                JSONObject user = jsonUsers.getJSONObject(i);
                Person p = new Person(user);
                MainActivity.attendees.add(p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainActivity.sortData(MainActivity.SortMethod.ALPHABETICAL);
        MainActivity.populateRecycler();
    }

    /*
        Convert the downloaded data to a usable String
     */
    private static StringBuilder inputStreamToString(InputStream is) {
        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }
}
