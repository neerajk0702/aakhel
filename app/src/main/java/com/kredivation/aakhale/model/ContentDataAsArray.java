package com.kredivation.aakhale.model;

import java.util.ArrayList;

public class ContentDataAsArray {
    private boolean status;
    private ArrayList<Data> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo [status = " + status + ", data = " + data + "]";
    }
}
