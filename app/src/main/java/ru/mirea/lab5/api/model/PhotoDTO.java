package ru.mirea.lab5.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhotoDTO {
    public static Double imagesCount = 1.0;
    public static Double limit = 0.0;
    private static int pageCount = 1;
    public static String breeds_id = "";
    private int isLike = -1;

    private int id;

    @SerializedName("url")
    @Expose
    private String imageUrl;

    public PhotoDTO(String imageUrl, int isLike) {
        this.imageUrl = imageUrl;
        this.isLike = isLike;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public static int getPageCount() {
        pageCount = (int)Math.ceil(imagesCount / limit);
        return pageCount;
    }

    public String getImageId() {
        int pos1 = imageUrl.lastIndexOf("/");
        int pos2 = imageUrl.lastIndexOf(".");
        String s = imageUrl.substring(pos1 + 1, pos2);
        System.out.println(imageUrl);
        return s;
    }

    public int isLike() {
        return isLike;
    }

    public void setLike(int like) {
        isLike = like;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
