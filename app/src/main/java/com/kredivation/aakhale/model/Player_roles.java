package com.kredivation.aakhale.model;

public class Player_roles {
    private long id;

    private int is_active;

    private String role_name;

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

    public String getRole_name ()
    {
        return role_name;
    }

    public void setRole_name (String role_name)
    {
        this.role_name = role_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", is_active = "+is_active+", role_name = "+role_name+"]";
    }
}


