package com.kredivation.aakhale.model;

import java.security.acl.Group;
import java.util.ArrayList;

public class ContentData {
private int id;
    private String status;

    private Data data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }
    private String message;
    private long token;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }
    private ArrayList<Sports> sports;

    private ArrayList<State> state;
    private ArrayList<Ground> ground;

    private ArrayList<Timezone> timezone;
    public ArrayList<Sports> getSports() {
        return sports;
    }

    public void setSports(ArrayList<Sports> sports) {
        this.sports = sports;
    }

    public ArrayList<State> getState() {
        return state;
    }

    public void setState(ArrayList<State> state) {
        this.state = state;
    }

    public ArrayList<Ground> getGround() {
        return ground;
    }

    public void setGround(ArrayList<Ground> ground) {
        this.ground = ground;
    }

    public ArrayList<Timezone> getTimezone() {
        return timezone;
    }

    public void setTimezone(ArrayList<Timezone> timezone) {
        this.timezone = timezone;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", data = "+data+"]";
    }
}
