package ru.mirea.lab5.api.model;

import com.google.gson.annotations.SerializedName;

public class PostType {
    @SerializedName("mime_type")
    private String mime_type;

    public String getIdAndMineType(String post_image_id) {
    int index = mime_type.indexOf("/");
    return  "https://cdn2.thecatapi.com/images/" + post_image_id + mime_type.substring(index + 1);
    }


}
