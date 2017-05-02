
package com.newsreader.thenewsreader.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sources implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sources")
    @Expose
    private ArrayList<Source> sources = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sources() {
    }

    /**
     * 
     * @param status
     * @param sources
     */
    public Sources(String status, ArrayList<Source> sources) {
        super();
        this.status = status;
        this.sources = sources;
    }

    protected Sources(Parcel in) {
        status = in.readString();
    }

    public static final Creator<Sources> CREATOR = new Creator<Sources>() {
        @Override
        public Sources createFromParcel(Parcel in) {
            return new Sources(in);
        }

        @Override
        public Sources[] newArray(int size) {
            return new Sources[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Source> getSources() {
        return sources;
    }

    public void setSources(ArrayList<Source> sources) {
        this.sources = sources;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
    }
}
