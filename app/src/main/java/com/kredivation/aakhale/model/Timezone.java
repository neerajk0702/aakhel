package com.kredivation.aakhale.model;

public class Timezone {

    String id;
    String timezone_name;
    String is_active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimezone_name() {
        return timezone_name;
    }

    public void setTimezone_name(String timezone_name) {
        this.timezone_name = timezone_name;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }
}
