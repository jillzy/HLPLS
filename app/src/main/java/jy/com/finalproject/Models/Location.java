package jy.com.finalproject.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("lat")
    @Expose
    public Float lat;
    @SerializedName("lng")
    @Expose
    public Float lng;

}