package ru.mirea.lab5.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostCreate {
//    @SerializedName("id")
//    @Expose
//    private String id;

    @SerializedName("image_id")
    @Expose
    private String image_id;

    @SerializedName("sub_id")
    @Expose
    private String sub_id;

    @SerializedName("value")
    private int value;

//    @SerializedName("mime_type")
//    private String mime_type;

    public PostCreate(String sub_id, String image_id) {
        this.sub_id = sub_id;
        this.image_id = image_id;
    }
//    public String getId() {
//        return id;
//    }

    public void setValue(int value) {
        this.value = value;
    }

//    public String getSub_id() {
//        return sub_id;
//    }

    public int getValue() {
        return value;
    }

    public String getImageId() {
        return image_id;
    }

//    public String getIdAndMineType(String post_image_id) {
//        int index = mime_type.indexOf("/");
//        return  "https://cdn2.thecatapi.com/images/" + post_image_id + mime_type.substring(index + 1);
//    }
}
