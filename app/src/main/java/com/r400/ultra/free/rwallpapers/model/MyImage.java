package com.r400.ultra.free.rwallpapers.model;

import java.io.Serializable;

/**
 * Created by Whitesteel on 30mm2016.
 */
public class MyImage implements Serializable {

    private String category;
    private String tags;
    private String resolution;
    private String weight;
    private String low, high;
    private String timestamp;



    public MyImage(){
    }

    public MyImage(String category,String tags,String resolution, String weight, String low, String high, String timestamp ){
        this.category = category;
        this.tags = tags;
        this.resolution = resolution;
        this.weight = weight;
        this.low = low;
        this.high = high;
        this.timestamp = timestamp;

    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
