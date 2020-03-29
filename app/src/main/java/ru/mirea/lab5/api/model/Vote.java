package ru.mirea.lab5.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vote {
    @SerializedName("id")
    @Expose
    private int id;

    public Vote(int vote_id) {
        this.id = vote_id;
    }

    public int getVote_id() {
        return id;
    }
}
