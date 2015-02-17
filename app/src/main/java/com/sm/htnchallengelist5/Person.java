package com.sm.htnchallengelist5;

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
    private List<Skill> skills;

    public Person(String name, String picture, String company, String email, String phone, double lat, double lon, List<Skill> skills) {
        this.name = name;
        this.picture = picture;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.lat = lat;
        this.lon = lon;
        this.skills = skills;
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