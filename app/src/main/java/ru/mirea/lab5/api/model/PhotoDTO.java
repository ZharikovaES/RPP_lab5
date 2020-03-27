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
    private boolean isLike = true;

    @SerializedName("url")
    @Expose
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }
    public static int getPageCount() {
        pageCount = (int)Math.ceil(imagesCount / limit);
        return pageCount;
    }

    public String getUrl() {
        int pos1 = imageUrl.lastIndexOf("/");
        int pos2 = imageUrl.lastIndexOf(".");
        return imageUrl.substring(pos1 + 1, pos2);
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
