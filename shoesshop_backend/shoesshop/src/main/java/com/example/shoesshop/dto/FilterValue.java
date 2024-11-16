package com.example.shoesshop.dto;

public class FilterValue {
    private String size;
    private String color;
    private int typeid;

    public FilterValue() {
        this.typeid = 0;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }
}
