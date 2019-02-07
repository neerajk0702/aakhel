package com.kredivation.aakhale.model;

import java.util.ArrayList;

public class Data {
    private long id;
    private String unique_id;
    private String email;
    private String created_at;
    private String updated_at;
    private int role;
    private int is_active;
    private String full_name;
    private String mobile;
    private String date_of_birth;
    private int gender;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private int users_sports;
    private String profile;
    private String experience;
    private int player_role;
    private String profile_picture;
    private String auth_token;

    public boolean isSelectValue() {
        return selectValue;
    }

    public void setSelectValue(boolean selectValue) {
        this.selectValue = selectValue;
    }

    private boolean selectValue=false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public int getUsers_sports() {
        return users_sports;
    }

    public void setUsers_sports(int users_sports) {
        this.users_sports = users_sports;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getPlayer_role() {
        return player_role;
    }

    public void setPlayer_role(int player_role) {
        this.player_role = player_role;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

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
