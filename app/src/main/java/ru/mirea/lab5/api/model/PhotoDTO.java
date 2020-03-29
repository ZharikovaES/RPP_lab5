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

    private int iid;

    @SerializedName("url")
    private String url;

    public PhotoDTO(String imageUrl, int isLike) {
        this.url = imageUrl;
        this.isLike = isLike;
    }

    public String getImageUrl() {
        return url;
    }
    public static int getPageCount() {
        pageCount = (int)Math.ceil(imagesCount / limit);
        return pageCount;
    }

    public String getImageId() {
        int pos1 = url.lastIndexOf("/");
        int pos2 = url.lastIndexOf(".");
        String s = url.substring(pos1 + 1, pos2);
        return s;
    }

    public int isLike() {
        return isLike;
    }

    public void setLike(int like) {
        isLike = like;
    }

    public int getId() {
        return iid;
    }

    public void setId(int id) {
        this.iid = id;
    }
}
