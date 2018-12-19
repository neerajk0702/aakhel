package com.kredivation.aakhale.model;

public class Academics {

    String name;
    String address;
    String moregame;
    String gameicon;


    public Academics(String name, String address, String moregame, String gameicon) {
        this.name = name;
        this.address = address;
        this.moregame = moregame;
        this.gameicon = gameicon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMoregame() {
        return moregame;
    }

    public void setMoregame(String moregame) {
        this.moregame = moregame;
    }

    public String getGameicon() {
        return gameicon;
    }

    public void setGameicon(String gameicon) {
        this.gameicon = gameicon;
    }
}
