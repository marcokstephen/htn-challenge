package com.sm.htnchallengelist5;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stephen on 15-02-16.
 */

public class Skill{
    private String sname;
    private int rating;

    Skill(String sname, int rating) {
        this.sname = sname;
        this.rating = rating;
    }

    Skill(String jsonSkill){
        try {
            JSONObject jSkill = new JSONObject(jsonSkill);
            this.sname = jSkill.getString("name");
            this.rating = jSkill.getInt("rating");
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        JSONObject jSkill = new JSONObject();
        try{
            jSkill.put("name",this.sname);
            jSkill.put("rating",this.sname);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jSkill.toString();
    }

    public String getSname() {
        return sname;
    }

    public int getRating() {
        return rating;
    }
}