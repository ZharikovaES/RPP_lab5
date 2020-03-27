package ru.mirea.lab5.api.model;

import com.google.gson.annotations.SerializedName;

public class PostFavourites {
    @SerializedName("sub_id")
    private String sub_id;

    @SerializedName("value")
    private int value;

    @SerializedName("image_id")
    private String image_id;



    public PostFavourites(String sub_id, int value, String image_id) {
        this.sub_id = sub_id;
        this.value = value;
        this.image_id = image_id;
    }

    public String getSub_id() {
        return sub_id;
    }

    public int getValue() {
        return value;
    }

    public String getImage_id() {
        return image_id;
    }
}
