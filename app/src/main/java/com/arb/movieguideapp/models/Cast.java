package com.arb.movieguideapp.models;

public class Cast {

    private String name;
    private int img_link;

    public Cast(String name, int img_link) {
        this.name = name;
        this.img_link = img_link;
    }

    public String getName() {
        return name;
    }

    public int getImg_link() {
        return img_link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg_link(int img_link) {
        this.img_link = img_link;
    }
}
