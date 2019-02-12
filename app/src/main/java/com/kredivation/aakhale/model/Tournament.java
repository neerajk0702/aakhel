package com.kredivation.aakhale.model;

import org.json.JSONArray;

public class Tournament {

    private String end_date;

    private String unique_id;

    private int is_active;

    private String rules_regulations;

    private String tournament_zipcode;

    private String format;

    private String display_picture;

    private String created_at;

    private String tournament_country;

    private String overs;

    private String tournament_city;

    private String entry_fees;

    private String updated_at;

    private String about_tournament;

    private String user_id;

    private String tournament_state;

    private String tournament_address;

    private String prizes;

    private String name;

    private int id;

    private String facilities;

    private String start_date;

    private int status;

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }


    public String getRules_regulations() {
        return rules_regulations;
    }

    public void setRules_regulations(String rules_regulations) {
        this.rules_regulations = rules_regulations;
    }

    public String getTournament_zipcode() {
        return tournament_zipcode;
    }

    public void setTournament_zipcode(String tournament_zipcode) {
        this.tournament_zipcode = tournament_zipcode;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDisplay_picture() {
        return display_picture;
    }

    public void setDisplay_picture(String display_picture) {
        this.display_picture = display_picture;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTournament_country() {
        return tournament_country;
    }

    public void setTournament_country(String tournament_country) {
        this.tournament_country = tournament_country;
    }

    public String getOvers() {
        return overs;
    }

    public void setOvers(String overs) {
        this.overs = overs;
    }

    public String getTournament_city() {
        return tournament_city;
    }

    public void setTournament_city(String tournament_city) {
        this.tournament_city = tournament_city;
    }

    public String getEntry_fees() {
        return entry_fees;
    }

    public void setEntry_fees(String entry_fees) {
        this.entry_fees = entry_fees;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getAbout_tournament() {
        return about_tournament;
    }

    public void setAbout_tournament(String about_tournament) {
        this.about_tournament = about_tournament;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTournament_state() {
        return tournament_state;
    }

    public void setTournament_state(String tournament_state) {
        this.tournament_state = tournament_state;
    }

    public String getTournament_address() {
        return tournament_address;
    }

    public void setTournament_address(String tournament_address) {
        this.tournament_address = tournament_address;
    }

    public String getPrizes() {
        return prizes;
    }

    public void setPrizes(String prizes) {
        this.prizes = prizes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    String CompleteAddress;

    public String getCompleteAddress() {
        return CompleteAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        CompleteAddress = completeAddress;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
private JSONArray tournamentTeam;

    public JSONArray getTournamentTeam() {
        return tournamentTeam;
    }

    public void setTournamentTeam(JSONArray tournamentTeam) {
        this.tournamentTeam = tournamentTeam;
    }

    @Override
    public String toString() {
        return "ClassPojo [end_date = " + end_date + ", unique_id = " + unique_id + ", is_active = " + is_active + ", rules_regulations = " + rules_regulations + ", tournament_zipcode = " + tournament_zipcode + ", format = " + format + ", display_picture = " + display_picture + ", created_at = " + created_at + ", tournament_country = " + tournament_country + ", overs = " + overs + ", tournament_city = " + tournament_city + ", entry_fees = " + entry_fees + ", updated_at = " + updated_at + ", about_tournament = " + about_tournament + ", user_id = " + user_id + ", tournament_state = " + tournament_state + ", tournament_address = " + tournament_address + ", prizes = " + prizes + ", name = " + name + ", id = " + id + ", facilities = " + facilities + ", start_date = " + start_date + ", status = " + status + "]";
    }


}