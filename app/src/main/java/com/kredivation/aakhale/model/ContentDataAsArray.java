package com.kredivation.aakhale.model;

import java.util.ArrayList;

public class ContentDataAsArray {
    private boolean status;
    private ArrayList<Data> data;
private long total_pages;
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

    public long getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(long total_pages) {
        this.total_pages = total_pages;
    }

    @Override
    public String toString() {
        return "ClassPojo [status = " + status + ", data = " + data + "]";
    }
}
