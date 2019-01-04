package com.kredivation.aakhale.model;

public class Users_roles {
    private long id;

    private int is_active;

    private String user_type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getUser_type ()
    {
        return user_type;
    }

    public void setUser_type (String user_type)
    {
        this.user_type = user_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", is_active = "+is_active+", user_type = "+user_type+"]";
    }
}


