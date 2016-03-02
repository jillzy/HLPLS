package jy.com.finalproject.Models;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("results")
    @Expose
    public List<Result> results = new ArrayList<Result>();
    @SerializedName("status")
    @Expose
    public String status;

}
