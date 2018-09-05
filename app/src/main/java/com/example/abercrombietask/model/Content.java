package com.example.abercrombietask.model;

import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("title")
    private String title;

    @SerializedName("target")
    private String target;

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setTarget(String target){
        this.target = target;
    }

    public String getTarget(){
        return target;
    }


    @Override
    public String toString(){
        return
                "ContentItem{" +
                        "title = '" + title + '\'' +
                        ",target = '" + target + '\'' +
                        "}";
    }
}
