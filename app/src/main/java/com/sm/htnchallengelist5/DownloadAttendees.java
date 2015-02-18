package com.sm.htnchallengelist5;

import android.os.AsyncTask;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.attendees = new ArrayList<Person>();

        try {
            JSONObject response = new JSONObject(output);
            JSONArray jsonUsers = response.getJSONArray("users");
            for (int i = 0; i < jsonUsers.length(); i++) {
                JSONObject user = jsonUsers.getJSONObject(i);
                String name = user.getString("name");
                String picture = user.getString("picture");
                String company = user.getString("company");
                String email = user.getString("email");
                String phone = user.getString("phone");
                double lat = user.getDouble("latitude");
                double lon = user.getDouble("longitude");
                JSONArray skills = user.getJSONArray("skills");
                List<Skill> userSkills = new ArrayList<Skill>();
                for (int j = 0; j < skills.length(); j++) {
                    JSONObject jsonSkill = skills.getJSONObject(j);
                    String skillName = jsonSkill.getString("name");
                    int skillRating = jsonSkill.getInt("rating");
                    Skill s = new Skill(skillName, skillRating);
                    userSkills.add(s);
                }

                Collections.sort(userSkills, new Comparator<Skill>() {
                    @Override
                    public int compare(Skill lhs, Skill rhs) {
                        return rhs.getRating() - lhs.getRating();
                    }
                });

                Person p = new Person(name, picture, company, email, phone, lat, lon, userSkills);
                MainActivity.attendees.add(p);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainActivity.sortData(1); //default sort is (1) which is alphabetical
        MainActivity.populateRecycler();
    }

    public static StringBuilder inputStreamToString(InputStream is) {
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
