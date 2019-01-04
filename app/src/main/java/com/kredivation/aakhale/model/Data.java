package com.kredivation.aakhale.model;

import java.util.ArrayList;

public class Data {
    private ArrayList<Player_roles> player_roles;

    private ArrayList<Users_roles> users_roles;

    private ArrayList<Sports> sports;

    public ArrayList<Player_roles> getPlayer_roles() {
        return player_roles;
    }

    public void setPlayer_roles(ArrayList<Player_roles> player_roles) {
        this.player_roles = player_roles;
    }

    public ArrayList<Users_roles> getUsers_roles() {
        return users_roles;
    }

    public void setUsers_roles(ArrayList<Users_roles> users_roles) {
        this.users_roles = users_roles;
    }

    public ArrayList<Sports> getSports() {
        return sports;
    }

    public void setSports(ArrayList<Sports> sports) {
        this.sports = sports;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [player_roles = "+player_roles+", users_roles = "+users_roles+", sports = "+sports+"]";
    }
}
