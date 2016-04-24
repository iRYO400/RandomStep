package com.r400.ultra.free.rwallpapers.activity;

import java.io.Serializable;
import java.util.ArrayList;


public class Genres implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private boolean isSelected;
    private int imgRes;

    public Genres() {

    }

    public Genres(String name) {

        this.name = name;

    }

    public Genres(String name, boolean isSelected) {

        this.name = name;
        this.isSelected = isSelected;
    }

    public Genres(String name, boolean isSelected, int imageResource) {

        this.name = name;
        this.isSelected = isSelected;
        this.imgRes = imageResource;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }



}
