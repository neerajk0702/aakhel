package com.kredivation.aakhale.model;

import java.util.ArrayList;

public class State {
    private String id;

    private String is_active;

    private String country_id;

    private String state_name;

    private ArrayList<City> city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public ArrayList<City> getCity() {
        return city;
    }

    public void setCity(ArrayList<City> city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", is_active = " + is_active + ", country_id = " + country_id + ", state_name = " + state_name + ", city = " + city + "]";
    }
}