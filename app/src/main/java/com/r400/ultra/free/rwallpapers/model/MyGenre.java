package com.r400.ultra.free.rwallpapers.model;

import java.io.Serializable;
import java.util.ArrayList;


public class MyGenre implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private boolean isSelected;
    private String image;
    private String info;

    public MyGenre() {

    }
    public MyGenre(String name, boolean isSelected, String image, String info) {

        this.name = name;
        this.isSelected = isSelected;
        this.image = image;
        this.info = info;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
