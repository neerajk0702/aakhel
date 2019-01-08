package com.kredivation.aakhale.model;

public class City {
    private String id;

    private String state_id;

    private String city_name;

    private String is_active;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getState_id ()
    {
        return state_id;
    }

    public void setState_id (String state_id)
    {
        this.state_id = state_id;
    }

    public String getCity_name ()
    {
        return city_name;
    }

    public void setCity_name (String city_name)
    {
        this.city_name = city_name;
    }

    public String getIs_active ()
    {
        return is_active;
    }

    public void setIs_active (String is_active)
    {
        this.is_active = is_active;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", state_id = "+state_id+", city_name = "+city_name+", is_active = "+is_active+"]";
    }
}

