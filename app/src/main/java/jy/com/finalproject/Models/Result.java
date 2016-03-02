package jy.com.finalproject.Models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("formatted_address")
    @Expose
    public String formattedAddress;
}