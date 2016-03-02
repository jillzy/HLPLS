package jy.com.finalproject.Models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressComponent {

    @SerializedName("long_name")
    @Expose
    public String longName;
    @SerializedName("short_name")
    @Expose
    public String shortName;
    @SerializedName("types")
    @Expose
    public List<String> types = new ArrayList<String>();

}