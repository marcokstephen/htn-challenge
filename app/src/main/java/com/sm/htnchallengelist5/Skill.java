package com.sm.htnchallengelist5;

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

    public String getSname() {
        return sname;
    }

    public int getRating() {
        return rating;
    }
}