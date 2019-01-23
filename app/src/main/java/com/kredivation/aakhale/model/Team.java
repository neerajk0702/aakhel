package com.kredivation.aakhale.model;

public class Team {


    int id, is_active;
    String name, user_id;
            String unique_id, created_at, updated_at, about_team, team_city, team_state, team_country, team_zipcode, team_thumbnail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getAbout_team() {
        return about_team;
    }

    public void setAbout_team(String about_team) {
        this.about_team = about_team;
    }

    public String getTeam_city() {
        return team_city;
    }

    public void setTeam_city(String team_city) {
        this.team_city = team_city;
    }

    public String getTeam_state() {
        return team_state;
    }

    public void setTeam_state(String team_state) {
        this.team_state = team_state;
    }

    public String getTeam_country() {
        return team_country;
    }

    public void setTeam_country(String team_country) {
        this.team_country = team_country;
    }

    public String getTeam_zipcode() {
        return team_zipcode;
    }

    public void setTeam_zipcode(String team_zipcode) {
        this.team_zipcode = team_zipcode;
    }

    public String getTeam_thumbnail() {
        return team_thumbnail;
    }

    public void setTeam_thumbnail(String team_thumbnail) {
        this.team_thumbnail = team_thumbnail;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
