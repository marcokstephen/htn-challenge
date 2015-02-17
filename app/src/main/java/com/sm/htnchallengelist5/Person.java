package com.sm.htnchallengelist5;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by stephen on 15-02-16.
 */
public class Person {

    private String name;
    private String picture;
    private String company;
    private String email;
    private String phone;
    private double lat;
    private double lon;
    private int distanceFromUw;
    private List<Skill> skills;
    private Bitmap bitpic;

    public Person(String name, String picture, String company, String email, String phone, double lat, double lon, List<Skill> skills) {
        this.name = name;
        this.picture = picture;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.lat = lat;
        this.lon = lon;
        this.skills = skills;
        this.bitpic = null;
        this.distanceFromUw = haversine(lat,lon);
    }

    public static int haversine(double lat2, double lon2) {
        double R = 6372.8; //radius of the earth
        double lat1 = 43.4729555; //lat of waterloo
        double lon1 = -80.5400489; // lon of waterloo

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return (int)(R * c);
    }

    public String getFirstSkillString(){
        if (skills.size() > 0){
            int rating = 10 - skills.get(0).getRating();
            return skills.get(0).getSname()+rating;
        } else {
            return "";
        }
    }

    public int getDistanceFromUw(){
        return this.distanceFromUw;
    }

    public void setBitpic(Bitmap pic){
        this.bitpic = pic;
    }
    public Bitmap getBitpic(){
        return this.bitpic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}