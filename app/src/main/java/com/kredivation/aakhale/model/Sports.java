package com.kredivation.aakhale.model;

public class Sports {
    private long id;

    private String sports_name;

    private int is_active;


    public String getSports_name ()
    {
        return sports_name;
    }

    public void setSports_name (String sports_name)
    {
        this.sports_name = sports_name;
    }

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

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", sports_name = "+sports_name+", is_active = "+is_active+"]";
    }
}


