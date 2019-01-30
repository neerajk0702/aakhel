package com.kredivation.aakhale.model;

public class Team_player {
    private String name;

    private String profile_picture;

    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [name = " + name + ", profile_picture = " + profile_picture + ", status = " + status + "]";
    }
}
