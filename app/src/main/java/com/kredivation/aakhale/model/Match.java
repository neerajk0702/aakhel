package com.kredivation.aakhale.model;

public class Match {
    private String match_type;
    private String is_active;
    private String match_city;
    private String match_country;
    private String match_state;
    private String format;
    private String ground_id;
    private String date;
    private int id;
    private String unique_id;
    private String match_zipcode;
    private String over;
    private String time;
    private String updated_at;
    private String tournament_id;
    private String name;
    private String created_at;
    private String user_id;
    private String match_address;

    public String getMatch_type() {
        return match_type;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getMatch_city() {
        return match_city;
    }

    public void setMatch_city(String match_city) {
        this.match_city = match_city;
    }

    public String getMatch_country() {
        return match_country;
    }

    public void setMatch_country(String match_country) {
        this.match_country = match_country;
    }

    public String getMatch_state() {
        return match_state;
    }

    public void setMatch_state(String match_state) {
        this.match_state = match_state;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getGround_id() {
        return ground_id;
    }

    public void setGround_id(String ground_id) {
        this.ground_id = ground_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getMatch_zipcode() {
        return match_zipcode;
    }

    public void setMatch_zipcode(String match_zipcode) {
        this.match_zipcode = match_zipcode;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(String tournament_id) {
        this.tournament_id = tournament_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMatch_address() {
        return match_address;
    }

    public void setMatch_address(String match_address) {
        this.match_address = match_address;
    }
    private String matchUmpire;
    private String matchteam;
    private String matchGround;
    private String matchCity;
    private String matchState;
    private String matchCountry;

    public String getMatchUmpire() {
        return matchUmpire;
    }

    public void setMatchUmpire(String matchUmpire) {
        this.matchUmpire = matchUmpire;
    }

    public String getMatchteam() {
        return matchteam;
    }

    public void setMatchteam(String matchteam) {
        this.matchteam = matchteam;
    }

    public String getMatchGround() {
        return matchGround;
    }

    public void setMatchGround(String matchGround) {
        this.matchGround = matchGround;
    }

    public String getMatchCity() {
        return matchCity;
    }

    public void setMatchCity(String matchCity) {
        this.matchCity = matchCity;
    }

    public String getMatchState() {
        return matchState;
    }

    public void setMatchState(String matchState) {
        this.matchState = matchState;
    }

    public String getMatchCountry() {
        return matchCountry;
    }

    public void setMatchCountry(String matchCountry) {
        this.matchCountry = matchCountry;
    }

    @Override
    public String toString() {
        return "ClassPojo [match_type = " + match_type + ", is_active = " + is_active + ", match_city = " + match_city + ", match_country = " + match_country + ", match_state = " + match_state + ", format = " + format + ", ground_id = " + ground_id + ", date = " + date + ", id = " + id + ", unique_id = " + unique_id + ", match_zipcode = " + match_zipcode + ", over = " + over + ", time = " + time + ", updated_at = " + updated_at + ", tournament_id = " + tournament_id + ", name = " + name + ", created_at = " + created_at + ", user_id = " + user_id + ", match_address = " + match_address + "]";
    }

}
