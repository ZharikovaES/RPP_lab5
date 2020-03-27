package ru.mirea.lab5.api.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BreedDTO {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public String getBreed() {
        return name;
    }
}
