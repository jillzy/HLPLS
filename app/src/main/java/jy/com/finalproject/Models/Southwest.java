package jy.com.finalproject.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Southwest {

    @SerializedName("lat")
    @Expose
    public Float lat;
    @SerializedName("lng")
    @Expose
    public Float lng;

}