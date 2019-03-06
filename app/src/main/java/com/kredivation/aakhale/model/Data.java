package com.kredivation.aakhale.model;

import org.json.JSONObject;

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
    private String sender_id;
    private String receiver_id;
    private String message;
    private String message_media;

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_media() {
        return message_media;
    }

    public void setMessage_media(String message_media) {
        this.message_media = message_media;
    }

    public boolean isSelectValue() {
        return selectValue;
    }

    public void setSelectValue(boolean selectValue) {
        this.selectValue = selectValue;
    }

    private boolean selectValue = false;

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


    private String umpireMatchArray;
    private String playerTeamArray;
    private String roleObj;
    private String coach_team;

    public String getCoach_team() {
        return coach_team;
    }

    public void setCoach_team(String coach_team) {
        this.coach_team = coach_team;
    }

    public String getUmpireMatchArray() {
        return umpireMatchArray;
    }

    public void setUmpireMatchArray(String umpireMatchArray) {
        this.umpireMatchArray = umpireMatchArray;
    }

    public String getPlayerTeamArray() {
        return playerTeamArray;
    }

    public void setPlayerTeamArray(String playerTeamArray) {
        this.playerTeamArray = playerTeamArray;
    }

    public String getRoleObj() {
        return roleObj;
    }

    public void setRoleObj(String roleObj) {
        this.roleObj = roleObj;
    }

    private String complateAddress;

    public String getComplateAddress() {
        return complateAddress;
    }

    public void setComplateAddress(String complateAddress) {
        this.complateAddress = complateAddress;
    }

    private String cityObj;
    private String stateObj;
    private String countryObj;

    public String getCityObj() {
        return cityObj;
    }

    public void setCityObj(String cityObj) {
        this.cityObj = cityObj;
    }

    public String getStateObj() {
        return stateObj;
    }

    public void setStateObj(String stateObj) {
        this.stateObj = stateObj;
    }

    public String getCountryObj() {
        return countryObj;
    }

    public void setCountryObj(String countryObj) {
        this.countryObj = countryObj;
    }

    private String usersSportArray;
    private String playerRoleObj;

    public String getUsersSportArray() {
        return usersSportArray;
    }

    public void setUsersSportArray(String usersSportArray) {
        this.usersSportArray = usersSportArray;
    }

    public String getPlayerRoleObj() {
        return playerRoleObj;
    }

    public void setPlayerRoleObj(String playerRoleObj) {
        this.playerRoleObj = playerRoleObj;
    }

    String about;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    String fee_per_match_day;
    String estdDate;

    public String getFee_per_match_day() {
        return fee_per_match_day;
    }

    public void setFee_per_match_day(String fee_per_match_day) {
        this.fee_per_match_day = fee_per_match_day;
    }

    public String getEstdDate() {
        return estdDate;
    }

    public void setEstdDate(String estdDate) {
        this.estdDate = estdDate;
    }

    String umpire_tournament;

    public String getUmpire_tournament() {
        return umpire_tournament;
    }

    public void setUmpire_tournament(String umpire_tournament) {
        this.umpire_tournament = umpire_tournament;
    }

    @Override
    public String toString() {
        return "ClassPojo [player_roles = " + player_roles + ", users_roles = " + users_roles + ", sports = " + sports + "]";
    }

    private Post_info post_info;

    private Writer_info writer_info;

    public Post_info getPost_info() {
        return post_info;
    }

    public void setPost_info(Post_info post_info) {
        this.post_info = post_info;
    }

    public Writer_info getWriter_info() {
        return writer_info;
    }

    public void setWriter_info(Writer_info writer_info) {
        this.writer_info = writer_info;
    }

    private String userId;
    private String notification_type;
    private JSONObject notificationData;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public JSONObject getNotificationData() {
        return notificationData;
    }

    public void setNotificationData(JSONObject notificationData) {
        this.notificationData = notificationData;
    }
}
